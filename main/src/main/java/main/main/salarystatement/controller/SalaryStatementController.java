package main.main.salarystatement.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.member.service.MemberService;
import main.main.salarystatement.dto.PreDto;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SalaryStatementController {
    private final SalaryStatementService salaryStatementService;
    private final SalaryStatementMapper salaryStatementMapper;

    @GetMapping("/manager/{company-id}/members/{companymember-id}/paystub")
    public ResponseEntity preSalaryStatement(@RequestParam int year, @RequestParam int month,
                                               @PathVariable("company-id") long companyId,
                                               @PathVariable("companymember-id") long companyMemberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        SalaryStatement salaryStatement = salaryStatementService.getPreContent(companyId, companyMemberId, year, month, authenticationMemberId);
        Object[] check = salaryStatementService.check(salaryStatement);
        SalaryStatement checkSalaryStatement = null;
        if ((Boolean) check[0]) {
            checkSalaryStatement = (SalaryStatement) check[1];
        }
        SalaryStatementDto.PreContent preContent = salaryStatementMapper.preContent(salaryStatement, (Boolean) check[0], checkSalaryStatement);
        return new ResponseEntity<>(preContent, HttpStatus.OK);
    }

    @PostMapping("/manager/{company-id}/members/{companymember-id}/paystub")
    public ResponseEntity postSalaryStatement(@RequestParam int year, @RequestParam int month,
                                              @PathVariable("company-id") long companyId,
                                              @PathVariable("companymember-id") long companyMemberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.createSalaryStatement(companyId, companyMemberId, year, month, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/paystub/{salarystatement-id}")
    public ResponseEntity getSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        SalaryStatement salaryStatement = salaryStatementService.findSalaryStatement(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(salaryStatementMapper.salaryStatementToResponse(salaryStatement), HttpStatus.OK);
    }

    @GetMapping("/worker/mypaystub")
    public ResponseEntity getSalaryStatements() {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        List<SalaryStatement> salaryStatements = salaryStatementService.findSalaryStatements(authenticationMemberId);

        return new ResponseEntity<>(salaryStatementMapper.salaryStatementToResponses(salaryStatements), HttpStatus.OK);
    }

    @DeleteMapping("/paystub/{salarystatement-id}")
    public ResponseEntity deleteSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.deleteSalaryStatement(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paystub/{salarystatement-id}/file")
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

    @PostMapping("/paystub/{salarystatement-id}/email")
    public ResponseEntity sendSalaryStatement(@PathVariable("salarystatement-id") long salaryStatementId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        salaryStatementService.sendEmail(salaryStatementId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
