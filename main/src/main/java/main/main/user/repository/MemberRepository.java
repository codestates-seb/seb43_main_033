package main.main.user.repository;

import main.main.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByResidentNumber(String residentNumber);
    Optional<Member> findByEmail(String email);
}
