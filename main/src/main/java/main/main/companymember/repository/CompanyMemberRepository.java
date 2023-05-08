package main.main.companymember.repository;

import main.main.companymember.entitiy.CompanyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
}
