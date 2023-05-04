package main.main.bank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import main.main.bank.repository.BankRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;


    public Bank findBank(long bankId) {
        return findVerifiedBank(bankId);
    }

    public Bank findVerifiedBank(long bankId) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        Bank findedBank = optionalBank.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BANK_NOT_FOUND));

        return findedBank;
    }

//    public Bank createBank(BankDto.Post request) {
//        Bank bank = new Bank();
//        Bank.BankGroup bankGroup = request.getBankGroup();
//
//        bank.setBankGroup(bankGroup);
//
//        return bankRepository.save(bank);
//    }

    public List<Bank> createAllBanks() {
        List<Bank> banks = new ArrayList<>();
        for (Bank.BankGroup bankGroup : Bank.BankGroup.values()) {
            Bank bank = new Bank();
            bank.setBankGroup(bankGroup);
            banks.add(bankRepository.save(bank));
        }
        return banks;
    }


}



//    public Bank.BankGroup findBanks(long bankId) {
//        return Arrays.stream(Bank.BankGroup.values())
//                .filter(bank -> bank.equals(bankId))
//                .findAny()
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BANK_NOT_FOUND));
//    }



//    public Bank saveBanks(long)
//        Bank bank = new Bank();
//    Bank.BankGroup.setBankName("국민은행");
//    Bank.BankGroup(Bank.BankGroup.국민.name());
//    bankRepository.save(bank);

//
//    public ResponseEntity<List<String>> getBanks() {
//        List<String> bankgroup = Arrays.stream(Bank.BankGroup.values())
//                .map(Bank.BankGroup::getBankName)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(bankgroup);



