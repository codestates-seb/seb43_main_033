package main.main.memberbank.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class MemberBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberBankId;
    private Long memberId;
    private Long bankId;
    private Long accountNumer;


}