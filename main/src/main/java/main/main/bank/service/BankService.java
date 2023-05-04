package main.main.bank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.entity.Bank;
import main.main.bank.repository.BankRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
