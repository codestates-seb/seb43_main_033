package main.main.salarystatement.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.service.LaborContractService;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.repository.SalaryStatementRepository;
import main.main.salarystatement.utils.Calculator;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
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
        DecimalFormat formatter = new DecimalFormat("###,###");
        // Define the PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, baos);

        BaseFont objBaseFont = BaseFont.createFont("font/ham.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font headFont = new Font(objBaseFont, 20);
        Font objFont = new Font(objBaseFont, 11);

        // Open the document and define the layout
        document.open();

        // Add the employee details
        String name = salaryStatement.getName();
        String team = salaryStatement.getTeam();
        String grade = salaryStatement.getGrade();
        BigDecimal hourlyWage = salaryStatement.getHourlyWage();
        BigDecimal basePay = salaryStatement.getBasePay();
        BigDecimal overtimePay = salaryStatement.getOvertimePay();
        int overtimePayBasis = salaryStatement.getOvertimePayBasis();
        BigDecimal nightWorkAllowance = salaryStatement.getNightWorkAllowance();
        int nightWorkAllowanceBasis = salaryStatement.getNightWorkAllowanceBasis();
        BigDecimal holidayWorkAllowance = salaryStatement.getHolidayWorkAllowance();
        int holidayWorkAllowanceBasis = salaryStatement.getHolidayWorkAllowanceBasis();
        BigDecimal unpaidLeave = salaryStatement.getUnpaidLeave();
        BigDecimal salary = salaryStatement.getSalary();
        BigDecimal incomeTax = salaryStatement.getIncomeTax();
        BigDecimal nationalCoalition = salaryStatement.getNationalCoalition();
        BigDecimal healthInsurance = salaryStatement.getHealthInsurance();
        BigDecimal employmentInsurance = salaryStatement.getEmploymentInsurance();
        BigDecimal totalSalary = salaryStatement.getTotalSalary();
        String bankName = salaryStatement.getBankName();
        String accountNumber = salaryStatement.getAccountNumber();
        String accountHolder = salaryStatement.getAccountHolder();

        PdfPTable table = new PdfPTable(5);	// 컬럼 갯수 지정.
        table.setWidthPercentage(100);	// Table 의 폭 %로 조절 가능.

        // 테이블 컬럼 폭 지정
        float[] colwidth = new float[]{15f, 25f, 20f, 25f, 20f};
        table.setWidths(colwidth);

        PdfPCell cell1 = new PdfPCell(new Phrase(salaryStatement.getYear() + "년 " + salaryStatement.getMonth() + "월 급여명세서", headFont));
        cell1.setColspan(5);
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setPadding(30);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("성명", objFont));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setPadding(10);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase(name, objFont));
        cell3.setColspan(2);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPadding(10);
        table.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Phrase("사번", objFont));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setPadding(10);
        table.addCell(cell4);

        PdfPCell cell5 = new PdfPCell(new Phrase("12345", objFont));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setPadding(10);
        table.addCell(cell5);

        PdfPCell cell6 = new PdfPCell(new Phrase("부서", objFont));
        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell6.setPadding(10);
        table.addCell(cell6);

        PdfPCell cell7 = new PdfPCell(new Phrase(team, objFont));
        cell7.setColspan(2);
        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell7.setPadding(10);
        table.addCell(cell7);

        PdfPCell cell8 = new PdfPCell(new Phrase("직급", objFont));
        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell8.setPadding(10);
        table.addCell(cell8);

        PdfPCell cell9 = new PdfPCell(new Phrase(grade, objFont));
        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell9.setPadding(10);
        table.addCell(cell9);

        PdfPCell cell10 = new PdfPCell(new Phrase(" ", objFont));
        cell10.setColspan(5);
        cell10.setBorderColor(BaseColor.WHITE);
        cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell10.setPadding(2);
        table.addCell(cell10);

        PdfPCell cell11 = new PdfPCell(new Phrase("세부 내역", objFont));
        cell11.setColspan(5);
        cell11.setGrayFill(0.9f);	// 셀 배경 지정.
        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell11.setPadding(10);
        table.addCell(cell11);

        PdfPCell cell12 = new PdfPCell(new Phrase("지급", objFont));
        cell12.setColspan(3);
        cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell12.setPadding(10);
        table.addCell(cell12);

        PdfPCell cell13 = new PdfPCell(new Phrase("공제", objFont));
        cell13.setColspan(2);
        cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell13.setPadding(10);
        table.addCell(cell13);

        PdfPCell cell14 = new PdfPCell(new Phrase("임금 항목", objFont));
        cell14.setColspan(2);
        cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell14.setPadding(10);
        table.addCell(cell14);

        PdfPCell cell15 = new PdfPCell(new Phrase("지급 금액(원)", objFont));
        cell15.setColspan(1);
        cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell15.setPadding(10);
        table.addCell(cell15);

        PdfPCell cell16 = new PdfPCell(new Phrase("공제 항목", objFont));
        cell16.setColspan(1);
        cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell16.setPadding(10);
        table.addCell(cell16);

        PdfPCell cell17 = new PdfPCell(new Phrase("공제 금액(원)", objFont));
        cell17.setColspan(1);
        cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell17.setPadding(10);
        table.addCell(cell17);

        PdfPCell cell100 = new PdfPCell(new Phrase("매월 지급", objFont));
        cell100.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell100.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell100.setRowspan(6);
        table.addCell(cell100);

        PdfPCell cell18 = new PdfPCell(new Phrase("기본급", objFont));
        cell18.setColspan(1);
        cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell18.setPadding(10);
        table.addCell(cell18);

        PdfPCell cell19 = new PdfPCell(new Phrase(formatter.format(basePay), objFont));
        cell19.setColspan(1);
        cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell19.setPadding(10);
        table.addCell(cell19);

        PdfPCell cell20 = new PdfPCell(new Phrase("소득세", objFont));
        cell20.setColspan(1);
        cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell20.setPadding(10);
        table.addCell(cell20);

        PdfPCell cell21 = new PdfPCell(new Phrase(formatter.format(incomeTax), objFont));
        cell21.setColspan(1);
        cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell21.setPadding(10);
        table.addCell(cell21);

        PdfPCell cell22 = new PdfPCell(new Phrase("연장근로수당", objFont));
        cell22.setColspan(1);
        cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell22.setPadding(10);
        table.addCell(cell22);

        PdfPCell cell23 = new PdfPCell(new Phrase(formatter.format(overtimePay), objFont));
        cell23.setColspan(1);
        cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell23.setPadding(10);
        table.addCell(cell23);

        PdfPCell cell24 = new PdfPCell(new Phrase("국민연금", objFont));
        cell24.setColspan(1);
        cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell24.setPadding(10);
        table.addCell(cell24);

        PdfPCell cell25 = new PdfPCell(new Phrase(formatter.format(nationalCoalition), objFont));
        cell25.setColspan(1);
        cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell25.setPadding(10);
        table.addCell(cell25);

        PdfPCell cell26 = new PdfPCell(new Phrase("야간근로수당", objFont));
        cell26.setColspan(1);
        cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell26.setPadding(10);
        table.addCell(cell26);

        PdfPCell cell27 = new PdfPCell(new Phrase(formatter.format(nightWorkAllowance), objFont));
        cell27.setColspan(1);
        cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell27.setPadding(10);
        table.addCell(cell27);

        PdfPCell cell28 = new PdfPCell(new Phrase("고용보험", objFont));
        cell28.setColspan(1);
        cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell28.setPadding(10);
        table.addCell(cell28);

        PdfPCell cell29 = new PdfPCell(new Phrase(formatter.format(employmentInsurance), objFont));
        cell29.setColspan(1);
        cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell29.setPadding(10);
        table.addCell(cell29);

        PdfPCell cell30 = new PdfPCell(new Phrase("휴일근로수당", objFont));
        cell30.setColspan(1);
        cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell30.setPadding(10);
        table.addCell(cell30);

        PdfPCell cell31 = new PdfPCell(new Phrase(formatter.format(holidayWorkAllowance), objFont));
        cell31.setColspan(1);
        cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell31.setPadding(10);
        table.addCell(cell31);

        PdfPCell cell32 = new PdfPCell(new Phrase("건강보험", objFont));
        cell32.setColspan(1);
        cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell32.setPadding(10);
        table.addCell(cell32);

        PdfPCell cell33 = new PdfPCell(new Phrase(formatter.format(healthInsurance), objFont));
        cell33.setColspan(1);
        cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell33.setPadding(10);
        table.addCell(cell33);

        PdfPCell cell34 = new PdfPCell(new Phrase("무급휴가", objFont));
        cell34.setColspan(1);
        cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell34.setPadding(10);
        table.addCell(cell34);

        PdfPCell cell35 = new PdfPCell(new Phrase(formatter.format(unpaidLeave), objFont));
        cell35.setColspan(1);
        cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell35.setPadding(10);
        table.addCell(cell35);

        PdfPCell cell36 = new PdfPCell(new Phrase(" ", objFont));
        cell36.setColspan(1);
        cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell36.setPadding(10);
        table.addCell(cell36);

        PdfPCell cell37 = new PdfPCell(new Phrase(" ", objFont));
        cell37.setColspan(1);
        cell37.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell37.setPadding(10);
        table.addCell(cell37);

        PdfPCell cell38 = new PdfPCell(new Phrase(" ", objFont));
        cell38.setColspan(1);
        cell38.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell38.setPadding(10);
        table.addCell(cell38);

        PdfPCell cell39 = new PdfPCell(new Phrase(" ", objFont));
        cell39.setColspan(1);
        cell39.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell39.setPadding(10);
        table.addCell(cell39);

        PdfPCell cell40 = new PdfPCell(new Phrase(" ", objFont));
        cell40.setColspan(1);
        cell40.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell40.setPadding(10);
        table.addCell(cell40);

        PdfPCell cell41 = new PdfPCell(new Phrase(" ", objFont));
        cell41.setColspan(1);
        cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell41.setPadding(10);
        table.addCell(cell41);

        PdfPCell cell42 = new PdfPCell(new Phrase("지급액 계", objFont));
        cell42.setColspan(2);
        cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell42.setPadding(10);
        table.addCell(cell42);

        PdfPCell cell43 = new PdfPCell(new Phrase(formatter.format(salary), objFont));
        cell43.setColspan(1);
        cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell43.setPadding(10);
        table.addCell(cell43);

        PdfPCell cell44 = new PdfPCell(new Phrase("공제액 계", objFont));
        cell44.setColspan(1);
        cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell44.setPadding(10);
        table.addCell(cell44);

        PdfPCell cell45 = new PdfPCell(new Phrase(formatter.format(salary.subtract(totalSalary)), objFont));
        cell45.setColspan(1);
        cell45.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell45.setPadding(10);
        table.addCell(cell45);

        PdfPCell cell46 = new PdfPCell(new Phrase(" ", objFont));
        cell46.setColspan(3);
        cell46.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell46.setBorderColor(BaseColor.WHITE);
        cell46.setPadding(10);
        table.addCell(cell46);

        PdfPCell cell47 = new PdfPCell(new Phrase("실수령액(원)", objFont));
        cell47.setColspan(1);
        cell47.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell47.setPadding(10);
        table.addCell(cell47);

        PdfPCell cell48 = new PdfPCell(new Phrase(formatter.format(totalSalary), objFont));
        cell48.setColspan(1);
        cell48.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell48.setPadding(10);
        table.addCell(cell48);

        PdfPCell cell49 = new PdfPCell(new Phrase(" ", objFont));
        cell49.setColspan(5);
        cell49.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell49.setBorderColor(BaseColor.WHITE);
        cell49.setPadding(2);
        table.addCell(cell49);

        PdfPCell cell50 = new PdfPCell(new Phrase("계산 방법", objFont));
        cell50.setColspan(5);
        cell50.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell50.setPadding(10);
        cell50.setGrayFill(0.9f);	// 셀 배경 지정.
        table.addCell(cell50);

        PdfPCell cell51 = new PdfPCell(new Phrase("구분", objFont));
        cell51.setColspan(2);
        cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell51.setPadding(10);
        table.addCell(cell51);

        PdfPCell cell52 = new PdfPCell(new Phrase("산출식 또는 산출방법", objFont));
        cell52.setColspan(2);
        cell52.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell52.setPadding(10);
        table.addCell(cell52);

        PdfPCell cell53 = new PdfPCell(new Phrase("지급액(원)", objFont));
        cell53.setColspan(1);
        cell53.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell53.setPadding(10);
        table.addCell(cell53);

        PdfPCell cell54 = new PdfPCell(new Phrase("연장근로수당", objFont));
        cell54.setColspan(2);
        cell54.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell54.setPadding(10);
        table.addCell(cell54);

        PdfPCell cell55 = new PdfPCell(new Phrase("연장근로시간 수(" + overtimePayBasis +"시간)*" +
                formatter.format(hourlyWage) + "원*" + StatusOfWork.Note.연장근로.getRate(), objFont));
        cell55.setColspan(2);
        cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell55.setPadding(10);
        table.addCell(cell55);

        PdfPCell cell56 = new PdfPCell(new Phrase(formatter.format(overtimePay), objFont));
        cell56.setColspan(1);
        cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell56.setPadding(10);
        table.addCell(cell56);

        PdfPCell cell57 = new PdfPCell(new Phrase("야간근로수당", objFont));
        cell57.setColspan(2);
        cell57.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell57.setPadding(10);
        table.addCell(cell57);

        PdfPCell cell58 = new PdfPCell(new Phrase("야간근로시간 수(" + nightWorkAllowanceBasis +"시간)*" +
                formatter.format(hourlyWage) + "원*" + StatusOfWork.Note.야간근로.getRate(), objFont));
        cell58.setColspan(2);
        cell58.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell58.setPadding(10);
        table.addCell(cell58);

        PdfPCell cell59 = new PdfPCell(new Phrase(formatter.format(nightWorkAllowance), objFont));
        cell59.setColspan(1);
        cell59.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell59.setPadding(10);
        table.addCell(cell59);

        PdfPCell cell60 = new PdfPCell(new Phrase("휴일근로수당", objFont));
        cell60.setColspan(2);
        cell60.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell60.setPadding(10);
        table.addCell(cell60);

        PdfPCell cell61 = new PdfPCell(new Phrase("휴일근로시간 수(" + holidayWorkAllowanceBasis +"시간)*" +
                formatter.format(hourlyWage) + "원*" + StatusOfWork.Note.휴일근로.getRate(), objFont));
        cell61.setColspan(2);
        cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell61.setPadding(10);
        table.addCell(cell61);

        PdfPCell cell62 = new PdfPCell(new Phrase(formatter.format(holidayWorkAllowance), objFont));
        cell62.setColspan(1);
        cell62.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell62.setPadding(10);
        table.addCell(cell62);

        PdfPCell cell63 = new PdfPCell(new Phrase(" ", objFont));
        cell63.setColspan(5);
        cell63.setBorderColor(BaseColor.WHITE);
        cell63.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell63.setPadding(2);
        table.addCell(cell63);

        PdfPCell cell64 = new PdfPCell(new Phrase("지급 정보", objFont));
        cell64.setColspan(5);
        cell64.setGrayFill(0.9f);
        cell64.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell64.setPadding(9);
        table.addCell(cell64);

        PdfPCell cell65 = new PdfPCell(new Phrase(bankName + " " + accountNumber + " / 예금주: " + accountHolder, objFont));
        cell65.setColspan(5);
        cell65.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell65.setPadding(9);
        table.addCell(cell65);

        document.add(table);
        document.close();

        return baos;
    }

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

            log.info("Success");
        } catch (MessagingException e) {
            log.info("fail");
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
