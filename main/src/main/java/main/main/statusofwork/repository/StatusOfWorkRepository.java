package main.main.statusofwork.repository;

import main.main.member.entity.Member;
import main.main.statusofwork.entity.StatusOfWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatusOfWorkRepository extends JpaRepository<StatusOfWork, Long> {
    List<StatusOfWork> findByMemberAndStartTimeBetween(Member member, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
