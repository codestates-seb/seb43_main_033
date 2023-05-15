package main.main.salarystatement.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
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

    @PostMapping
    public ResponseEntity postSalaryStatement(@RequestBody SalaryStatementDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.createSalaryStatement(salaryStatementMapper.postToSalaryStatement(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{salarystatement-id}")
    public ResponseEntity getSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        SalaryStatement salaryStatement = salaryStatementService.findSalaryStatement(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(salaryStatementMapper.salaryStatementToResponse(salaryStatement), HttpStatus.OK);
    }

    @DeleteMapping("/{salarystatement-id}")
    public ResponseEntity deleteSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.deleteSalaryStatement(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{salarystatement-id}/payslip")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable("salarystatement-id") long salaryStatementId ) throws Exception {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        SalaryStatement salaryStatement = salaryStatementService.findSalaryStatement(salaryStatementId, authenticationMemberId);
        ByteArrayOutputStream baos = salaryStatementService.makePdf(salaryStatement);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(salaryStatement.getYear() + "/" + salaryStatement.getMonth() + ".pdf", salaryStatement.getYear() + "/" + salaryStatement.getMonth() + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    @PostMapping("/{salarystatement-id}/payslip")
    public ResponseEntity sendSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.sendEmail(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
