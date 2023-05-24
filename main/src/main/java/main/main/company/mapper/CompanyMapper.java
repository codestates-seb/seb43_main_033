package main.main.company.mapper;

import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.mapper.CompanyMemberMapper;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMemberMapper companyMemberMapper = Mappers.getMapper(CompanyMemberMapper.class);


    default Company companyPostToCompany(CompanyDto.Post requestBody) {
        Company company = new Company();
        Member member = new Member();

        company.setCompanyName(requestBody.getCompanyName());
        company.setCompanySize(requestBody.getCompanySize());
        company.setAddress(requestBody.getAddress());
        company.setBusinessNumber(requestBody.getBusinessNumber());
        company.setInformation(requestBody.getInformation());
        company.setMemberId(member.getMemberId());

        return company;
    }

    default Company companyPatchToCompany(CompanyDto.Patch requestBody) {
        Company company = new Company();

        company.setCompanyId(requestBody.getCompanyId());
        company.setCompanySize(requestBody.getCompanySize());
        company.setBusinessNumber(requestBody.getBusinessNumber());
        company.setAddress(requestBody.getAddress());
        company.setInformation(requestBody.getInformation());
        company.setCompanyName(requestBody.getCompanyName());

        return company;

    }

    default CompanyDto.Response companyToCompanyResponse(Company company, BigDecimal[] salaryInfo, List<CompanyMember> companyMembers) {
        List<CompanyMemberDto.ResponseForList> companyMemberDto = companyMembers.isEmpty() ? null :
                company.getCompanyMembers().stream()
                        .map(companyMember -> companyMemberMapper.companyMemberToCompanyMemberResponseForList(companyMember))
                        .collect(Collectors.toList());

        BigDecimal totalSalaryOfCompanyThisMonth = salaryInfo[0];
        BigDecimal totalSalaryOfCompanyLastMonth = salaryInfo[1];

        return CompanyDto.Response.builder()
                .companyId(company.getCompanyId())
                .memberId(company.getMemberId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .companyMembers(companyMemberDto)
                .theSalaryOfTheCompanyThisMonth(totalSalaryOfCompanyThisMonth)
                .theSalaryOfTheCompanyLastMonth(totalSalaryOfCompanyLastMonth)
                .build();
    }

    default CompanyDto.ResponseForList companyToCompanyResponseForList(Company company, List<CompanyMember> companyMembers) {
        List<CompanyMemberDto.ResponseForList> companyMemberDto = companyMembers.isEmpty() ? null :
                company.getCompanyMembers().stream()
                        .map(companyMember -> companyMemberMapper.companyMemberToCompanyMemberResponseForList(companyMember))
                        .collect(Collectors.toList());

        return CompanyDto.ResponseForList.builder()
                .companyId(company.getCompanyId())
                .memberId(company.getMemberId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .companyMembers(companyMemberDto)
                .build();
    }

    default List<CompanyDto.ResponseForList> companiesToCompaniesResponse(List<Company> companies) {
        return companies.stream()
                .map(company -> companyToCompanyResponseForList(company, company.getCompanyMembers()))
                .collect(Collectors.toList());
    }




//    default CompanyDto.ResponseForSalary companyToCompanyResponseForSalary(Company company, BigDecimal totalSalaryOfCompany) {
//        return CompanyDto.ResponseForSalary.builder()
//                .companyId(company.getCompanyId())
//                .companyName(company.getCompanyName())
//                .totalSalaryOfCompany(totalSalaryOfCompany)
//                .build();
//    }


}