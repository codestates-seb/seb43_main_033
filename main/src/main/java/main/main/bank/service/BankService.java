package main.main.bank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.entity.Bank;
import main.main.bank.repository.BankRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    @PostConstruct
    public List<Bank> initializeAllBanks() {
        List<Bank> banks = new ArrayList<>();
        if (bankRepository.count() == 0) {
            for (Bank.BankGroup bankGroup : Bank.BankGroup.values()) {
                Bank bank = new Bank();
                bank.setBankGroup(bankGroup);
                banks.add(bankRepository.save(bank));
            }
        }
        return banks;
    }

    public Bank findBank(long bankId) {
        return findVerifiedBank(bankId);
    }

    public Bank findVerifiedBank(long bankId) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        Bank findedBank = optionalBank.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BANK_NOT_FOUND));

        return findedBank;
    }

    public Page<Bank> findBanks(int page, int size) {
        return bankRepository.findAll(PageRequest.of(page, size, Sort.by("bankId").ascending()));
    }



}
