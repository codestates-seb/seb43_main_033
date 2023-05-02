package main.main.bank.controller;

import lombok.RequiredArgsConstructor;
import main.main.bank.mapper.BankMapper;
import main.main.bank.service.BankService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/banks")
@RestController
@RequiredArgsConstructor
public class BankController {
    private final static String BANK_DEFAULT_URL = "/banks";
    private final BankService bankService;
    private final BankMapper bankMapper;


}
