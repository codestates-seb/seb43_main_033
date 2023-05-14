package main.main.member.entity;

import lombok.*;
import main.main.bank.entity.Bank;
import main.main.companymember.entity.CompanyMember;
import main.main.member.dto.Position;
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
    private String residentNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Position position;

//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "member",  fetch = FetchType.EAGER)
    private List<CompanyMember> companyMembers = new ArrayList<>();
    public void addCompanyMember(CompanyMember companyMember) {this.companyMembers.add(companyMember); }

    @OneToMany
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) {
        this.salaryStatements.add(salaryStatement);
    }


    @OneToMany(mappedBy = "member")
    private List<MemberBank> memberBanks = new ArrayList<>();
    public void addMemberBank(MemberBank memberBank) { this.memberBanks.add(memberBank); }

}
