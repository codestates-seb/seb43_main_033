package main.main.salarystatement.controller;

import lombok.RequiredArgsConstructor;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salarystatement")
@RequiredArgsConstructor
public class SalaryStatementController {
    private final SalaryStatementService salaryStatementService;
    private final SalaryStatementMapper salaryStatementMapper;
}
