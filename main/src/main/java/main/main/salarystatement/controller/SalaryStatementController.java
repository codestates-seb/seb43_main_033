package main.main.salarystatement.controller;

import lombok.RequiredArgsConstructor;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salarystatements")
@RequiredArgsConstructor
public class SalaryStatementController {
    private final SalaryStatementService salaryStatementService;
    private final SalaryStatementMapper salaryStatementMapper;

    @PostMapping
    public ResponseEntity postSalaryStatement(@RequestBody SalaryStatementDto.Post requestBody) {
        salaryStatementService.createSalaryStatement(salaryStatementMapper.postToSalaryStatement(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{salarystatement-id}")
    public ResponseEntity getSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        SalaryStatement salaryStatement = salaryStatementService.findSalaryStatement(salaryStatementId);

        return new ResponseEntity<>(salaryStatementMapper.salaryStatementToResponse(salaryStatement), HttpStatus.OK);
    }

    @DeleteMapping("/{salarystatement-id}")
    public ResponseEntity deleteSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        salaryStatementService.deleteSalaryStatement(salaryStatementId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
