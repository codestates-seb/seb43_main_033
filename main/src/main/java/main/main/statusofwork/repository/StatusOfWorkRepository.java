package main.main.statusofwork.repository;

import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import main.main.statusofwork.entity.StatusOfWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatusOfWorkRepository extends JpaRepository<StatusOfWork, Long> {
    List<StatusOfWork> findByMemberAndStartTimeBetween(Member member, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
    @Query("SELECT s FROM StatusOfWork s WHERE s.companyMember = :companyMember AND s.startTime < :nowForStart AND s.finishTime > :nowForEnd")
    List<StatusOfWork> findAllByCompanyMemberMemberAndStartTimeIsBeforeAndFinishTimeIsAfter(CompanyMember companyMember, LocalDateTime nowForStart, LocalDateTime nowForEnd);

}
