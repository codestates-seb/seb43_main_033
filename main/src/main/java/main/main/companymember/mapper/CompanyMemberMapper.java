package main.main.companymember.mapper;

import main.main.company.entity.Company;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMemberMapper {

    default CompanyMember companyMemberPostToCompanyMember(CompanyMemberDto.Post requestBody) {
        CompanyMember companyMember = new CompanyMember();
        Company company = new Company();
        Member member  = new Member();

        company.setCompanyId(requestBody.getCompanyId());
        member.setMemberId(requestBody.getMemberId());

        companyMember.setCompany(company);
        companyMember.setMember(member);
        companyMember.setGrade(requestBody.getGrade());
        companyMember.setTeam(requestBody.getTeam());

        return companyMember;
    }

    default CompanyMember companyMemberPacthToCompanyMember(CompanyMemberDto.Patch requestBody) {
        CompanyMember companyMember = new CompanyMember();
        Company company = new Company();
        Member member = new Member();

        company.setCompanyId(requestBody.getCompanyId());
        member.setMemberId(requestBody.getMemberId());

        companyMember.setCompany(company);
        companyMember.setMember(member);
        companyMember.setCompanyMemberId(requestBody.getCompanyMemberId());
        companyMember.setGrade(requestBody.getGrade());
        companyMember.setTeam(requestBody.getTeam());

        return companyMember;
    }

    default CompanyMemberDto.Response companyMemberToCompanyMemberResponse(CompanyMember companyMember) {
        return CompanyMemberDto.Response.builder()
                .companyMemberId(companyMember.getCompanyMemberId())
                .companyId(companyMember.getCompany().getCompanyId())
                .memberId(companyMember.getMember().getMemberId())
                .grade(companyMember.getGrade())
                .team(companyMember.getTeam())
                .status(companyMember.getStatus())
                .roles(companyMember.getRoles())
                .build();
    }
    default CompanyMemberDto.ResponseForList companyMemberToCompanyMemberResponseForList(CompanyMember companyMember) {
        return CompanyMemberDto.ResponseForList.builder()
                .companyMemberId(companyMember.getCompanyMemberId())
                .companyId(companyMember.getCompany().getCompanyId())
                .memberId(companyMember.getMember().getMemberId())
                .grade(companyMember.getGrade())
                .team(companyMember.getTeam())
                .status(companyMember.getStatus())
                .build();
    }


    default List<CompanyMemberDto.ResponseForList> companyMembersToCompanyMembersResponse(List<CompanyMember> companyMembers) {
        return  companyMembers.stream()
                .map(companyMember -> companyMemberToCompanyMemberResponseForList(companyMember))
                .collect(Collectors.toList());
    }

    default List<String> companyMemberToRoles(CompanyMemberDto.Roles roles) {
        return roles.getRoles();
    }

    CompanyMemberDto.Roles companyMemberToRolesResponse(CompanyMember companyMember);
}
