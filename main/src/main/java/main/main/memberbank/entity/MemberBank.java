package main.main.memberbank.entity;

import lombok.*;
import main.main.bank.entity.Bank;
import main.main.member.entity.Member;
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
public class MemberBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long memberBankId;
    private String accountNumber;
    private boolean isMainAccount;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    @OneToMany
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) {
        this.salaryStatements.add(salaryStatement);
    }


    public void setMember(Member member) {
        this.member = member;
        if(!member.getMemberBanks().contains(this)) {
            member.getMemberBanks().add(this);
        }
    }
}