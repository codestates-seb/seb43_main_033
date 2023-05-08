package main.main.company.mapper;

import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    default Company companyPostToCompany(CompanyDto.Post requestBody) {
        Company company = new Company();

        company.setCompanyName(requestBody.getCompanyName());
        company.setCompanySize(requestBody.getCompanySize());
        company.setBusinessNumber(requestBody.getBusinessNumber());
        company.setAddress(requestBody.getAddress());
        company.setInformation(requestBody.getInformation());

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

    default CompanyDto.Response companyToCompanyResponse(Company company) {
        return CompanyDto.Response.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .build();
    }

    default CompanyDto.ResponseForList companiesToCompanyResponseForList(Company company) {
        return CompanyDto.ResponseForList.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .build();
    }
    default List<CompanyDto.ResponseForList> companiesToCompanyResponses(List<Company> companies) {
        return companies.stream()
                .map(company -> companiesToCompanyResponseForList(company))
                .collect(Collectors.toList());
    }
}