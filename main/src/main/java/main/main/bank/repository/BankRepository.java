package main.main.bank.repository;

import main.main.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankRepository extends JpaRepository<Bank, Long> {

}


