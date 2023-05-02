package main.main.member.mapper;

import main.main.member.dto.MemberDto;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);
    MemberDto.Response memberToMemberResponse(Member member);
    Member responserPatchToMember(MemberDto.Patch requestBody, long authenticationUserId);

    MemberDto.Response memberPatchToMember(Member member);
}
