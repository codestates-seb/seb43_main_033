package main.main.company.mapper;

import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    default Company companyPostToCompany(CompanyDto.Post requestBody) {
        Company company = new Company();

        company.setCompanyName(requestBody.getCompanyName());
        company.setCompanySize(requestBody.getCompanySize());
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

    default CompanyDto.Response companyToCompanyResponse(Company company, BigDecimal[] salaryInfo) {

        BigDecimal totalSalaryOfCompanyThisMonth = salaryInfo[0];
        BigDecimal totalSalaryOfCompanyLastMonth = salaryInfo[1];

        return CompanyDto.Response.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .theSalaryOfTheCompanyThisMonth(totalSalaryOfCompanyThisMonth)
                .theSalaryOfTheCompanyLastMonth(totalSalaryOfCompanyLastMonth)
                .build();
    }

    default CompanyDto.ResponseForList companyToCompanyResponseForList(Company company, BigDecimal[] salaryInfo) {
        BigDecimal totalSalaryOfCompanyThisMonth = salaryInfo[0];
        BigDecimal totalSalaryOfCompanyLastMonth = salaryInfo[1];

        return CompanyDto.ResponseForList.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companySize(company.getCompanySize())
                .businessNumber(company.getBusinessNumber())
                .address(company.getAddress())
                .information(company.getInformation())
                .theSalaryOfTheCompanyThisMonth(totalSalaryOfCompanyThisMonth)
                .theSalaryOfTheCompanyLastMonth(totalSalaryOfCompanyLastMonth)
                .build();
    }

    default List<CompanyDto.ResponseForList> companiesToCompaniesResponse(List<Company> companies, Map<Long, BigDecimal[]> salaryInfoMap) {
        return companies.stream()
                .map(company -> {
                    BigDecimal[] salaryInfo = salaryInfoMap.getOrDefault(company.getCompanyId(), new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
                    return companyToCompanyResponseForList(company, salaryInfo);
                })
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