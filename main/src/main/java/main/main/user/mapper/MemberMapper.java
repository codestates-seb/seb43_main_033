package main.main.user.mapper;

import main.main.user.dto.MemberDto;
import main.main.user.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {


    Member memberPostToMember(MemberDto.Post requestBody);
    MemberDto.Response memberToMemberResponse(Member member);
    Member responserPatchToMember(MemberDto.Patch requestBody, long authenticationUserId);

    MemberDto.Response memberPatchToMember(Member member);
}
