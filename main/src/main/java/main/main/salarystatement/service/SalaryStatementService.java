package main.main.salarystatement.service;

import lombok.RequiredArgsConstructor;
import main.main.salarystatement.repository.SalaryStatementRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalaryStatementService {
    private final SalaryStatementRepository salaryStatementRepository;
}
