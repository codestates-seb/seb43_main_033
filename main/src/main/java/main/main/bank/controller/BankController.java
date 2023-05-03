package main.main.bank.controller;

import lombok.RequiredArgsConstructor;
import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import main.main.bank.mapper.BankMapper;
import main.main.bank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;


@RequestMapping("/banks")
@RestController
@RequiredArgsConstructor
public class BankController {
    private final static String BANK_DEFAULT_URL = "/banks";
    private final BankService bankService;
    private final BankMapper bankMapper;


    @PostMapping
    public ResponseEntity<BankDto.Response> postBank() {

        bankService.createAllBanks();

    return new ResponseEntity<>(HttpStatus.OK);
}

    @GetMapping("/{bank-id}")
    public ResponseEntity getBank(@PathVariable("bank-id") @Positive long bankId) {

        Bank bank = bankService.findBank(bankId);

        return new ResponseEntity<>(bankMapper.bankToBankResponse(bank),HttpStatus.OK);

    }

}
