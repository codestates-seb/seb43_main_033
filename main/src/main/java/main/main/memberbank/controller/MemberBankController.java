package main.main.memberbank.controller;


import lombok.RequiredArgsConstructor;
import main.main.memberbank.mapper.MemberBankMapper;
import main.main.memberbank.service.MemberBankService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/memberbank")
@RestController
@RequiredArgsConstructor
public class MemberBankController {

    private final static String MEMBERBANK_DEFAULT_URL = "/memberbank";
    private final MemberBankService memberBankService;
    private final MemberBankMapper memberBankMapper;

}
