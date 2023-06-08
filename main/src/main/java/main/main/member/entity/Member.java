package main.main.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.laborcontract.entity.LaborContract;
import main.main.memberbank.entity.MemberBank;
import main.main.salarystatement.entity.SalaryStatement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long memberId;

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String birthday;
    private String residentNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "member")
    @JsonIgnoreProperties
    private List<LaborContract> laborContracts = new ArrayList<>();
    public void addLaborContract(LaborContract laborContract) {
        this.laborContracts.add(laborContract);
    }

    @OneToMany(fetch =FetchType.EAGER ,mappedBy = "member")
    @JsonIgnore
    @JsonIgnoreProperties("members")
    private List<CompanyMember> companyMembers = new ArrayList<>();
    public void addCompanyMember(CompanyMember companyMember) {this.companyMembers.add(companyMember); }

    @OneToMany(mappedBy = "member")
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) {
        this.salaryStatements.add(salaryStatement);
    }


    @OneToMany(mappedBy = "member")
    @JsonIgnoreProperties("member")
    private List<MemberBank> memberBanks = new ArrayList<>();
    public void addMemberBank(MemberBank memberBank) { this.memberBanks.add(memberBank); }

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

}
