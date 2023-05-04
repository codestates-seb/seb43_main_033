package main.main.bank.repository;

import main.main.bank.entity.Bank;
import main.main.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

//    Optional<Bank.BankGroup> findByBankId(Long bankId);

}


