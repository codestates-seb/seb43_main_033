package main.main.membercompany.controller;

import lombok.RequiredArgsConstructor;
import main.main.membercompany.mapper.MemberCompanyMapper;
import main.main.membercompany.service.MemberCompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/membercompanies")
@RestController
@RequiredArgsConstructor
public class MemberCompanyController {

    private final MemberCompanyService memberCompanyService;
    private final MemberCompanyMapper memberCompanyMapper;

}
