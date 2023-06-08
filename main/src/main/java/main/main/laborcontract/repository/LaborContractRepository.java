package main.main.laborcontract.repository;

import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.laborcontract.entity.LaborContract;
import main.main.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LaborContractRepository extends JpaRepository<LaborContract, Long> {
    Optional<LaborContract> findLaborContractByMemberAndCompanyAndEndOfContractAfter(Member member,
                                                                                     Company company,
                                                                                     LocalDateTime thisMonth);

    List<LaborContract> findLaborContractByCompanyMember(CompanyMember companyMember);
}
