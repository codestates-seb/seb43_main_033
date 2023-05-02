package main.main.member.repository;

import main.main.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByResidentNumber(String residentNumber);
    Optional<Member> findByEmail(String email);
}
