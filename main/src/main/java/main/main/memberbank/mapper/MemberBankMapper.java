package main.main.memberbank.mapper;

import main.main.bank.entity.Bank;
import main.main.member.entity.Member;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberBankMapper {

    default MemberBank memberBankPostToMemberBank(MemberBankDto.Post requestBody) {
        MemberBank memberBank = new MemberBank();
        Member member = new Member();
        Bank bank = new Bank();

        member.setMemberId(requestBody.getMemberId());
        bank.setBankId(requestBody.getBankId());

        memberBank.setMember(member);
        memberBank.setBank(bank);
        memberBank.setAccountNumber(requestBody.getAccountNumber());



        return memberBank;
    }


    default MemberBank memberBankPathToMemberBank(MemberBankDto.Patch requestBody) {
        MemberBank memberBank = new MemberBank();
        Member member = new Member();
        Bank bank = new Bank();

        memberBank.setMember(member);
        memberBank.setBank(bank);
        memberBank.setMemberBankId(requestBody.getMemberBankId());
        memberBank.setAccountNumber(requestBody.getAccountNumber());

        return memberBank;
    }


    default MemberBankDto.Response memberBankToMemberBankResponse(MemberBank memberBank) {
        return MemberBankDto.Response.builder()
                .memberBankId(memberBank.getMemberBankId())
                .bankId(memberBank.getBank().getBankId())
                .bankName(memberBank.getBank().getBankGroup().getBankName())
                .accountNumber(memberBank.getAccountNumber())
                .memberId(memberBank.getMember().getMemberId())
                .build();
    }
}
