package main.main.member.mapper;

import main.main.member.dto.MemberDto;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);
    MemberDto.Response memberToMemberResponse(Member member);
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
                .roles(member.getRoles())
                .build();
    }
}
