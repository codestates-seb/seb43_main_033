package main.main.bank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
}
