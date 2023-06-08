package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.dto.ListPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequestMapping("/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final static String COMPANY_DEFAULT_URL = "/companies";
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PostMapping
    public ResponseEntity postCompany(@Valid @RequestBody CompanyDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        companyService.createCompany(companyMapper.companyPostToCompany(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{company-id}")
    public ResponseEntity patchCompany(@Positive @PathVariable("company-id") long companyId,
                                       @Valid @RequestBody CompanyDto.Patch requestBody) {

        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        requestBody.setCompanyId(companyId);

        companyService.updateCompany(companyMapper.companyPatchToCompany(requestBody), requestBody.getBusinessNumber(), authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{company-id}")
    public ResponseEntity getCompany(@PathVariable("company-id") @Positive long companyId) {
        Company company = companyService.findCompany(companyId);
        BigDecimal[] SalaryOfCompany = companyService.salaryOfCompany(companyId);
        List<CompanyMember> companyMembers = company.getCompanyMembers();

        return new ResponseEntity<>(companyMapper.companyToCompanyResponse(company, SalaryOfCompany, companyMembers), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity getCompanies(@Positive @RequestParam int page, @RequestParam int size) {
        Page<Company> pageCompanies = companyService.findCompanies(page - 1, size);
        List<Company> companies = pageCompanies.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(companyMapper.companiesToCompaniesResponse(companies), pageCompanies), HttpStatus.OK);
    }

    @DeleteMapping("/{company-id}")
    public ResponseEntity deleteCompany(@Positive @PathVariable("company-id") long companyId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        companyService.deleteCompany(companyId, authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    @GetMapping("/businessnumber/{member-id}")
    public ResponseEntity<List<Map<String, Object>>> getBusinessNumberByMember(@PathVariable("member-id") @Positive long memberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        List<Map<String, Object>> business = companyService.findBusinessNumbersByRole(memberId, authenticationMemberId);
        return new ResponseEntity<>(business, HttpStatus.OK);
    }



}