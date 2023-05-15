package main.main.member.mapper;

import main.main.member.dto.MemberDto;
import main.main.member.entity.Member;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);
    default MemberDto.Response memberToMemberResponse(Member member) {
        return MemberDto.Response.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .residentNumber(member.getResidentNumber())
                .address(member.getAddress())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .bank(getMemberBankToMember(member.getMemberBanks()))
                .build();
    }

    default List<MemberBankDto.MemberBankList> getMemberBankToMember (List<MemberBank> memberBanks) {
        return memberBanks.stream()
                .map(memberBankList -> MemberBankDto.MemberBankList.builder()
                        .memberBankId(memberBankList.getMemberBankId())
                        .bankName(memberBankList.getBank().getBankGroup().getBankName())
                        .accountNumber(memberBankList.getAccountNumber())
                        .bankCode(memberBankList.getBank().getBankGroup().getBankCode())
                        .bankId(memberBankList.getBank().getBankId())
                        .build())
                .collect(Collectors.toList());
    }

    Member responserPatchToMember(MemberDto.Patch requestBody, long authenticationUserId);

    MemberDto.Response memberPatchToMember(Member member);

    default List<MemberDto.Response> membersToMemberResponses(List<Member> members) {
        return members.stream()
                .map(this::memberToMembersResponses)
                .collect(Collectors.toList());
    }

    default MemberDto.Response memberToMembersResponses(Member member) {
        return MemberDto.Response.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .residentNumber(member.getResidentNumber())
                .address(member.getAddress())
                .build();
    }
}
