package main.main.salarystatement.repository;

import main.main.companymember.entity.CompanyMember;
import main.main.salarystatement.entity.SalaryStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryStatementRepository extends JpaRepository<SalaryStatement, Long> {
    Optional<SalaryStatement> findByYearAndMonthAndCompanyMember(int year, int month, CompanyMember companyMember);
}
