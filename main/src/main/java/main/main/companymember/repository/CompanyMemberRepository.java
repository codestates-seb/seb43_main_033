package main.main.companymember.repository;

import main.main.company.entity.Company;
import main.main.companymember.dto.Authority;
import main.main.companymember.entity.CompanyMember;
import main.main.member.dto.Position;
import main.main.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
    CompanyMember findByMemberAndCompany(Member member, Company company);
    List<CompanyMember> findByAuthority(Authority authority);

}
