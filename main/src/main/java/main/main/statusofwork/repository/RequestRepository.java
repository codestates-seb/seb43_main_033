package main.main.statusofwork.repository;

import main.main.company.entity.Company;
import main.main.companymember.dto.Status;
import main.main.statusofwork.entity.RequestVacation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestVacation, Long> {
    List<RequestVacation> findAllByCompanyMember_CompanyAndStatus(Company company, Status status, Sort sort);
}
