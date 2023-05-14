package main.main.memberbank.repository;

import main.main.memberbank.entity.MemberBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberBankRepository extends JpaRepository<MemberBank, Long> {
    Optional<MemberBank> findByAccountNumber(String accountNumber);
}