package main.main.salarystatement.controller;

import lombok.RequiredArgsConstructor;
import main.main.member.service.MemberService;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/salarystatements")
@RequiredArgsConstructor
public class SalaryStatementController {
    private final SalaryStatementService salaryStatementService;
    private final SalaryStatementMapper salaryStatementMapper;
    private final MemberService memberService;

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

    @GetMapping("/{salarystatement-id}/payslip")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable("salarystatement-id") long salaaryStatementId ) throws Exception {
        ByteArrayOutputStream baos = salaryStatementService.makePdf(salaaryStatementId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("payslip.pdf", "payslip.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }
}
