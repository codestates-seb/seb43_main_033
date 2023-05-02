package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import main.main.dto.ListPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final static String COMPANY_DEFAULT_URL = "/companies";
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PostMapping
    public ResponseEntity postCompany(@Valid @RequestBody CompanyDto.Post requestBody) {
        Company company = companyService.createCompany(companyMapper.companyPostToCompany(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{companies-id}")
    public ResponseEntity patchCompany(@Positive @PathVariable("company-id") long companyId,
                                       @Valid @RequestBody CompanyDto.Patch requestBody) {


        requestBody.setCompanyId(companyId);
        companyService.updateCompany(companyMapper.companyPatchToCompany(requestBody));

        return new ResponseEntity<>(companyMapper
                .companyToCompanyResponse(companyService.findCompany(companyId)), HttpStatus.OK);
    }

    @GetMapping("/{companies-id}")
    public ResponseEntity getCompany(@PathVariable("company-id") @Positive long companyId) {
        Company company = companyService.findCompany(companyId);

        return new ResponseEntity<>(companyMapper
                .companyToCompanyResponse(company), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCompanies(@Positive @RequestParam int page, @RequestParam int size) {
        Page<Company> pageCompanies = companyService.findCompanies(page - 1, size);
        List<Company> companies = pageCompanies.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(companyMapper.companiesToCompanyResponses(companies),pageCompanies), HttpStatus.OK);
    }

    @DeleteMapping("/{companies-id}")
    public ResponseEntity deleteCompany(@Positive @PathVariable("company-id") long companyId) {
        companyService.deleteCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}