package main.main.member.mapper;

import javax.annotation.processing.Generated;
import main.main.member.dto.MemberDto;
import main.main.member.entity.Member;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-02T13:15:21+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( requestBody.getName() );
        member.setPhoneNumber( requestBody.getPhoneNumber() );
        member.setEmail( requestBody.getEmail() );
        member.setResidentNumber( requestBody.getResidentNumber() );
        member.setGrade( requestBody.getGrade() );
        member.setAddress( requestBody.getAddress() );

        return member;
    }

    @Override
    public MemberDto.Response memberToMemberResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response.ResponseBuilder response = MemberDto.Response.builder();

        response.memberId( member.getMemberId() );
        response.name( member.getName() );
        response.phoneNumber( member.getPhoneNumber() );
        response.email( member.getEmail() );
        response.residentNumber( member.getResidentNumber() );
        response.grade( member.getGrade() );
        response.address( member.getAddress() );

        return response.build();
    }

    @Override
    public Member responserPatchToMember(MemberDto.Patch requestBody, long authenticationUserId) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        if ( requestBody != null ) {
            member.setMemberId( requestBody.getMemberId() );
            member.setName( requestBody.getName() );
            member.setPhoneNumber( requestBody.getPhoneNumber() );
            member.setEmail( requestBody.getEmail() );
            member.setResidentNumber( requestBody.getResidentNumber() );
            member.setGrade( requestBody.getGrade() );
            member.setAddress( requestBody.getAddress() );
        }

        return member;
    }

    @Override
    public MemberDto.Response memberPatchToMember(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response.ResponseBuilder response = MemberDto.Response.builder();

        response.memberId( member.getMemberId() );
        response.name( member.getName() );
        response.phoneNumber( member.getPhoneNumber() );
        response.email( member.getEmail() );
        response.residentNumber( member.getResidentNumber() );
        response.grade( member.getGrade() );
        response.address( member.getAddress() );

        return response.build();
    }
}
