package main.main.memberbank.mapper;

import main.main.bank.entity.Bank;
import main.main.member.entity.Member;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

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
        memberBank.setMainAccount(requestBody.isMainAccount());


        return memberBank;
    }


    default MemberBank memberBankPathToMemberBank(MemberBankDto.Patch requestBody) {
        MemberBank memberBank = new MemberBank();
        Member member = new Member();
        Bank bank = new Bank();

        member.setMemberId(requestBody.getMemberId());
        bank.setBankId(requestBody.getBankId());

        memberBank.setMember(member);
        memberBank.setBank(bank);
        memberBank.setMemberBankId(requestBody.getMemberBankId());
        memberBank.setAccountNumber(requestBody.getAccountNumber());
        memberBank.setMainAccount(requestBody.isMainAccount());

        return memberBank;
    }


    default MemberBankDto.Response memberBankToMemberBankResponse(MemberBank memberBank) {
        return MemberBankDto.Response.builder()
                .memberBankId(memberBank.getMemberBankId())
                .bankId(memberBank.getBank().getBankId())
                .bankCode(memberBank.getBank().getBankGroup().getBankCode())
                .bankName(memberBank.getBank().getBankGroup().getBankName())
                .accountNumber(memberBank.getAccountNumber())
                .memberId(memberBank.getMember().getMemberId())
                .mainAccount(memberBank.isMainAccount())
                .build();
    }

    default MemberBankDto.ResponseForList memberBankToMemberBankResponseForList(MemberBank memberBank) {
        return MemberBankDto.ResponseForList.builder()
                .memberBankId(memberBank.getMemberBankId())
                .bankId(memberBank.getBank().getBankId())
                .bankCode(memberBank.getBank().getBankGroup().getBankCode())
                .bankName(memberBank.getBank().getBankGroup().getBankName())
                .accountNumber(memberBank.getAccountNumber())
                .memberId(memberBank.getMember().getMemberId())
                .mainAccount(memberBank.isMainAccount())
                .build();
    }

    default List<MemberBankDto.ResponseForList> memberBanksToMemberBanksResponse(List<MemberBank> memberBanks) {
        return memberBanks.stream()
                .map(memberBank -> memberBankToMemberBankResponseForList(memberBank))
                .collect(Collectors.toList());
    }
}
