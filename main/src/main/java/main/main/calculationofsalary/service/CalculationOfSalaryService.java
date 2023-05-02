package main.main.calculationofsalary.service;

import lombok.RequiredArgsConstructor;
import main.main.calculationofsalary.repository.CalculationOfSalaryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationOfSalaryService {
    private final CalculationOfSalaryRepository calculationOfSalaryRepository;
}
