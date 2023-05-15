package main.main.statusofwork.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
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
    private final MemberService memberService;
    private final CompanyService companyService;

    public void createStatusOfWork(StatusOfWork statusOfWork) {
        Member member = memberService.findMember(statusOfWork.getMember().getMemberId());
        Company company = companyService.findCompany(statusOfWork.getCompany().getCompanyId());

        statusOfWork.setMember(member);
        statusOfWork.setCompany(company);

        statusOfWorkRepository.save(statusOfWork);
    }

    public void updateStatusOfWork(long statusOfWorkId, StatusOfWork statusOfWork) {
        StatusOfWork findedStatusOfWork = findVerifiedStatusOfWork(statusOfWorkId);

        Optional.ofNullable(statusOfWork.getStartTime())
                .ifPresent(startTime -> findedStatusOfWork.setStartTime(startTime));
        Optional.ofNullable(statusOfWork.getFinishTime())
                .ifPresent(finishTime -> findedStatusOfWork.setFinishTime(finishTime));
        Optional.ofNullable(statusOfWork.getNote())
                .ifPresent(note -> findedStatusOfWork.setNote(note));

        statusOfWorkRepository.save(findedStatusOfWork);
    }

    public List<StatusOfWork> findStatusOfWorks(int year, int month, long memberId) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        Member member = memberService.findMember(memberId);

        return statusOfWorkRepository.findByMemberAndStartTimeBetween(member, startOfMonth, endOfMonth);
    }

    public void deleteStatusOfWork(long statusOfWorkId) {
        statusOfWorkRepository.delete(findVerifiedStatusOfWork(statusOfWorkId));
    }

    private StatusOfWork findVerifiedStatusOfWork(long statusOfWorkId) {
        Optional<StatusOfWork> optionalStatusOfWork = statusOfWorkRepository.findById(statusOfWorkId);
        StatusOfWork statusOfWork = optionalStatusOfWork.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_NOT_FOUND));

        return statusOfWork;
    }
}
