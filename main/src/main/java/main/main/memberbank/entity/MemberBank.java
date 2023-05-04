package main.main.memberbank.entity;

import lombok.*;
import main.main.bank.entity.Bank;
import main.main.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberBankId;
    private String accountNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    public void setMember(Member member) {
        this.member = member;
        if(!member.getMemberBanks().contains(this)) {
            member.getMemberBanks().add(this);
        }
    }
}