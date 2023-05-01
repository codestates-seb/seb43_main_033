package main.main.salarystatement.repository;

import main.main.salarystatement.entity.SalaryStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryStatementRepository extends JpaRepository<SalaryStatement, Long> {
}
