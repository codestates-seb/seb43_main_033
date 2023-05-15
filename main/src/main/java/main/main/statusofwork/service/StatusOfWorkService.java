package main.main.statusofwork.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.repository.StatusOfWorkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusOfWorkService {
    private final StatusOfWorkRepository statusOfWorkRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final MemberService memberService;
    private final CompanyService companyService;

    public void createStatusOfWork(StatusOfWork statusOfWork, long authenticationMemberId) {
        Member member = memberService.findMember(statusOfWork.getMember().getMemberId());
        Company company = companyService.findCompany(statusOfWork.getCompany().getCompanyId());

        checkPermission(authenticationMemberId, company);

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
        Member member = memberService.findMember(memberId);
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
}
