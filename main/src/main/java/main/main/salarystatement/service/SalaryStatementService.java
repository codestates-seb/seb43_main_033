package main.main.salarystatement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.repository.SalaryStatementRepository;
import main.main.salarystatement.utils.Calculator;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.utils.PdfMaker;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryStatementService {
    private final SalaryStatementRepository salaryStatementRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final Calculator calculator;
    private final PdfMaker pdfMaker;

    public SalaryStatement getPreContent(long companyId, long companyMemberId, int year, int month, long authenticationMemberId) {
        SalaryStatement salaryStatement = calculator.makeContent(companyId, companyMemberId, year, month);

        checkPermission(authenticationMemberId, salaryStatement.getCompany());

        return  salaryStatement;
    }

    public Object[] check(SalaryStatement salaryStatement) {
        Object[] result = new Object[2];
        boolean isSuccess = false;
        SalaryStatement statement = salaryStatementRepository.findByYearAndMonthAndCompanyMember(salaryStatement.getYear(), salaryStatement.getMonth(), salaryStatement.getCompanyMember()).orElse(null);

        if (statement != null) {
            isSuccess = true;
            result[1] = statement;
        }

        result[0] = isSuccess;
        return result;
    }

    public void createSalaryStatement(long companyId, long companyMemberId, int year, int month, long authenticationMemberId) {
        SalaryStatement salaryStatement = calculator.makeContent(companyId, companyMemberId, year, month);
        List<StatusOfWork> statusOfWorks = salaryStatement.getStatusOfWorks();

        checkPermission(authenticationMemberId, salaryStatement.getCompany());

        SalaryStatement savedSalaryStatement = salaryStatementRepository.save(salaryStatement);
        statusOfWorks.stream().forEach(statusOfWork -> statusOfWork.setSalaryStatement(savedSalaryStatement));
    }

    public SalaryStatement findSalaryStatement(long salaryStatementId, long authenticationMemberId) {
        SalaryStatement salaryStatement = findVerifiedSalaryStatement(salaryStatementId);

        checkGetPermission(authenticationMemberId, salaryStatement);

        return salaryStatement;
    }

    public List<SalaryStatement> findSalaryStatements(long authenticationMemberId) {
        Member member = memberService.findMember(authenticationMemberId);
        List<SalaryStatement> salaryStatements = member.getSalaryStatements();

        return salaryStatements.stream()
                .sorted(Comparator.comparing(SalaryStatement::getId).reversed())
                .collect(Collectors.toList());
    }

    public void deleteSalaryStatement(long salaryStatementId, long authenticationMemberId) {
        SalaryStatement salaryStatement = findVerifiedSalaryStatement(salaryStatementId);

        checkPermission(authenticationMemberId, salaryStatement.getCompany());

        salaryStatementRepository.delete(salaryStatement);
    }

    private SalaryStatement findVerifiedSalaryStatement(long salaryStatementId) {
        Optional<SalaryStatement> optionalSalaryStatement = salaryStatementRepository.findById(salaryStatementId);
        SalaryStatement salaryStatement = optionalSalaryStatement.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SALARYSTATEMENT_NOT_FOUND));

        return  salaryStatement;
    }

    public ByteArrayOutputStream makePdf(SalaryStatement salaryStatement) throws Exception {
        return pdfMaker.makePdf(salaryStatement);
    }

    @Async
    public void sendEmail(long salaryStatementId, long authenticationMemberId) {
        SalaryStatement salaryStatement = findVerifiedSalaryStatement(salaryStatementId);

        checkPermission(authenticationMemberId, salaryStatement.getCompany());

        String year = String.valueOf(salaryStatement.getYear());
        String month = String.valueOf(salaryStatement.getMonth());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(salaryStatement.getMember().getEmail());
            mimeMessageHelper.setSubject( year + "년 " + month + "월 급여 명세서");
            mimeMessageHelper.setText(setContext(year, month,"email"), true);
            javaMailSender.send(mimeMessage);

            log.info("SalaryStatement #{} Success", salaryStatementId);
        } catch (MessagingException e) {
            log.info("SalaryStatement #{} Fail", salaryStatementId);
            throw new RuntimeException(e);
        }
    }

    private String setContext(String year, String month, String type) {
        Context context = new Context();
        context.setVariable("year", year);
        context.setVariable("month", month);
        return templateEngine.process(type, context);
    }

    private void checkPermission(long authenticationMemberId, Company company) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, company);
        if (!companyMember.getRoles().contains("MANAGER")) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkGetPermission(long authenticationMemberId, SalaryStatement findedSalaryStatement) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, findedSalaryStatement.getCompany());
        if (!companyMember.getRoles().contains("MANAGER") || findedSalaryStatement.getMember().getMemberId() != authenticationMemberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}
