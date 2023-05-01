package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/companys")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final static String COMPANY_DEFAULT_URL = "/companys";
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
}
