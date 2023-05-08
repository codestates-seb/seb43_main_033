package main.main.companymember.controller;

import lombok.RequiredArgsConstructor;
import main.main.companymember.mapper.CompanyMemberMapper;
import main.main.companymember.service.CompanyMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/companymembers")
@RestController
@RequiredArgsConstructor
public class CompanyMemberController {

    private final CompanyMemberService companyMemberService;
    private final CompanyMemberMapper companyMemberMapper;

}
