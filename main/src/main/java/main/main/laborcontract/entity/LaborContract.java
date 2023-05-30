package main.main.laborcontract.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class LaborContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "COMPANY_MEMEBER_ID")
    private CompanyMember companyMember;

    private LocalDateTime startOfContract;

    private LocalDateTime endOfContract;

    private BigDecimal basicSalary;

    private LocalTime startTime;

    private LocalTime finishTime;

    private String information;

    private String laborContractUri;

    private String pureUri;
}
