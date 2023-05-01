package main.main.userbank.controller;


import lombok.RequiredArgsConstructor;
import main.main.bank.mapper.BankMapper;
import main.main.bank.service.BankService;
import main.main.userbank.mapper.UserBankMapper;
import main.main.userbank.service.UserBankService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/userbanks")
@RestController
@RequiredArgsConstructor
public class UserBankController {

    private final static String USERBANK_DEFAULT_URL = "/userbanks";
    private final UserBankService userBankService;
    private final UserBankMapper userBankMapper;

}
