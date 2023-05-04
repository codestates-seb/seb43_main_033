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
    private Long accountNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

}