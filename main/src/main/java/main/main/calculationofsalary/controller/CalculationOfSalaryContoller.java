package main.main.calculationofsalary.controller;

import lombok.RequiredArgsConstructor;
import main.main.calculationofsalary.mapper.CalculationOfSalaryMapper;
import main.main.calculationofsalary.service.CalculationOfSalaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculationofsalary")
@RequiredArgsConstructor
public class CalculationOfSalaryContoller {
    private final CalculationOfSalaryService calculationOfSalaryService;
    private final CalculationOfSalaryMapper calculationOfSalaryMapper;
}
