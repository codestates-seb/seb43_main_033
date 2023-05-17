package main.main.companymember.repository;

import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
    CompanyMember findByMemberAndCompany(Member member, Company company);

}
