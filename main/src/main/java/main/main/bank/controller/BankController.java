package main.main.bank.controller;

import lombok.RequiredArgsConstructor;
import main.main.bank.entity.Bank;
import main.main.bank.mapper.BankMapper;
import main.main.bank.service.BankService;
import main.main.dto.ListPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;


@RequestMapping("/banks")
@RestController
@RequiredArgsConstructor
public class BankController {
    private final static String BANK_DEFAULT_URL = "/banks";
    private final BankService bankService;
    private final BankMapper bankMapper;


    @GetMapping("/{bank-id}")
    public ResponseEntity getBank(@PathVariable("bank-id") @Positive long bankId) {

        Bank bank = bankService.findBank(bankId);

        return new ResponseEntity<>(bankMapper.bankToBankResponse(bank),HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity getBanks(@Positive @RequestParam int page, @RequestParam int size) {

        Page<Bank> pageBanks = bankService.findBanks(page - 1, size);
        List<Bank> banks = pageBanks.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(bankMapper.banksToBanksResponse(banks),pageBanks), HttpStatus.OK);
    }
}
