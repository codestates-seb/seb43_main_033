package main.main.membercompany.repository;

import main.main.membercompany.entitiy.MemberCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCompanyRepository extends JpaRepository<MemberCompany, Long> {
}
