package main.main.statusofwork.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.companymember.service.CompanyMemberService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.statusofwork.entity.RequestVacation;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.entity.Vacation;
import main.main.statusofwork.repository.RequestRepository;
import main.main.statusofwork.repository.StatusOfWorkRepository;
import main.main.statusofwork.repository.VacationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusOfWorkService {
    private final StatusOfWorkRepository statusOfWorkRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final RequestRepository requestRepository;
    private final VacationRepository vacationRepository;
    private final MemberService memberService;
    private final CompanyService companyService;
    private final CompanyMemberService companyMemberService;

    public void createStatusOfWork(StatusOfWork statusOfWork, long companyId, long companyMemberId, long authenticationMemberId) {
        CompanyMember companyMember = companyMemberService.findCompanyMember(companyMemberId);
        Company company = companyService.findCompany(companyId);
        Member member = companyMember.getMember();

        checkPermission(authenticationMemberId, company);

        statusOfWork.setCompanyMember(companyMember);
        statusOfWork.setMember(member);
        statusOfWork.setCompany(company);

        statusOfWorkRepository.save(statusOfWork);
    }

    private void createStatusOfWork(StatusOfWork statusOfWork) {
        CompanyMember companyMember = statusOfWork.getCompanyMember();
        Member member = companyMember.getMember();
        Company company = companyMember.getCompany();

        statusOfWork.setMember(member);
        statusOfWork.setCompany(company);

        statusOfWorkRepository.save(statusOfWork);
    }

    public void updateStatusOfWork(long statusOfWorkId, StatusOfWork statusOfWork, long authenticationMemberId) {
        StatusOfWork findedStatusOfWork = findVerifiedStatusOfWork(statusOfWorkId);

        checkPermission(authenticationMemberId, findedStatusOfWork);

        Optional.ofNullable(statusOfWork.getStartTime())
                .ifPresent(startTime -> findedStatusOfWork.setStartTime(startTime));
        Optional.ofNullable(statusOfWork.getFinishTime())
                .ifPresent(finishTime -> findedStatusOfWork.setFinishTime(finishTime));
        Optional.ofNullable(statusOfWork.getNote())
                .ifPresent(note -> findedStatusOfWork.setNote(note));

        statusOfWorkRepository.save(findedStatusOfWork);
    }

    public List<StatusOfWork> findStatusOfWorks(int year, int month, long memberId, long authenticationMemberId) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        Member member = memberService.findMember(authenticationMemberId);
        List<StatusOfWork> list = statusOfWorkRepository.findByMemberAndStartTimeBetween(member, startOfMonth, endOfMonth);

        if (list.size() == 0) {
            throw new BusinessLogicException(ExceptionCode.STATUS_NOT_FOUND);
        }
        checkPermission(authenticationMemberId, list.get(0));
        return list;
    }

    public List<StatusOfWork> findStatusOfWorks(int year, int month, long memberId) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        Member member = memberService.findMember(memberId);
        return statusOfWorkRepository.findByMemberAndStartTimeBetween(member, startOfMonth, endOfMonth);
    }

    public void deleteStatusOfWork(long statusOfWorkId, long authenticationMemberId) {
        StatusOfWork statusOfWork = findVerifiedStatusOfWork(statusOfWorkId);
        checkPermission(authenticationMemberId, statusOfWork);

        statusOfWorkRepository.delete(statusOfWork);
    }

    private StatusOfWork findVerifiedStatusOfWork(long statusOfWorkId) {
        Optional<StatusOfWork> optionalStatusOfWork = statusOfWorkRepository.findById(statusOfWorkId);
        StatusOfWork statusOfWork = optionalStatusOfWork.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_NOT_FOUND));

        return statusOfWork;
    }

    private void checkPermission(long authenticationMemberId, StatusOfWork statusOfWork) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, statusOfWork.getCompany());
        if (!companyMember.getRoles().contains("MANAGER") || statusOfWork.getMember().getMemberId() != authenticationMemberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkPermission(long authenticationMemberId, Company company) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, company);
        if (!companyMember.getRoles().contains("MANAGER")) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public void requestVacation(RequestVacation requestVacation, long authenticationMemberId) {
        Company company = companyService.findCompany(requestVacation.getCompanyMember().getCompany().getCompanyId());
        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(member, company);
        Vacation vacation = companyMember.getVacation();

        int count = (int) ChronoUnit.DAYS.between(requestVacation.getVacationStart(), requestVacation.getVacationEnd()) + 1;
        if (count > vacation.getCount()) {
            throw new BusinessLogicException(ExceptionCode.CAN_NOT_OVER_VACATION_COUNT);
        }
        vacation.setCount(vacation.getCount() - count);
        requestVacation.setVacation(vacation);
        requestVacation.setCompanyMember(companyMember);

        requestRepository.save(requestVacation);
    }

    public void reviewRequestVacation(long requestId, String status, long authenticationMemberId) {
        RequestVacation request = findVerifiedRequest(requestId);
        Company company = request.getCompanyMember().getCompany();
        Member member = memberService.findMember(authenticationMemberId);

        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (!companyMember.getRoles().contains("MANAGER")) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        if (status.equals("approved")) {
            request.setStatus(Status.APPROVED);
            StatusOfWork statusOfWork = new StatusOfWork();
            statusOfWork.setStartTime(request.getVacationStart().atStartOfDay());
            statusOfWork.setFinishTime(request.getVacationEnd().atTime(LocalTime.MAX).minusSeconds(1));
            statusOfWork.setNote(StatusOfWork.Note.유급휴가);
            statusOfWork.setCompanyMember(request.getCompanyMember());
            createStatusOfWork(statusOfWork);
        } else if (status.equals("refuse")) {
            request.setStatus(Status.REFUSE);
            int count = (int) ChronoUnit.DAYS.between(request.getVacationStart(), request.getVacationEnd()) + 1;
            Vacation vacation = request.getVacation();
            vacation.setCount(vacation.getCount() + count);
            vacationRepository.save(vacation);
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_STATUS);
        }

        requestRepository.save(request);
    }

    public List<RequestVacation> getRequestList(long companyId) {
        Company company = companyService.findCompany(companyId);
        return requestRepository.findAllByCompanyMember_CompanyAndStatus(company, Status.PENDING, Sort.by("id").descending());
    }


    private RequestVacation findVerifiedRequest(long requestId) {
        Optional<RequestVacation> optionalRequestVacation = requestRepository.findById(requestId);
        RequestVacation requestVacation = optionalRequestVacation.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REQUEST_NOT_FOUND));

        return requestVacation;
    }
}
