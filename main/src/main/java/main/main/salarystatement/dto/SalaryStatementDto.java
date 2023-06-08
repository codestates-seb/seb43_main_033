package main.main.salarystatement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class SalaryStatementDto {
    @Getter
    @Setter
    @Builder
    public static class PreContent {
        private PreDto.Member member;
        private List<PreDto.Status> status;
        private PreDto.Statement statement;
        private boolean exist;
        private long salaryStatementId;
    }

    @Getter
    @Setter
    public static class Post {
        private long companyId;
        private long memberId;
        private int year;
        private int month;
    }

    @Getter
    @Builder
    public static class Response {
        private long id;
        private long companyId;
        private String companyName;
        private long memberId;
        private int year;
        private int month;
        private String name;
        private String team;
        private String grade;
        private BigDecimal hourlyWage;
        private BigDecimal basePay;
        private BigDecimal overtimePay;
        private int overtimePayBasis;
        private BigDecimal nightWorkAllowance;
        private int nightWorkAllowanceBasis;
        private BigDecimal holidayWorkAllowance;
        private int holidayWorkAllowanceBasis;
        private BigDecimal unpaidLeave;
        private BigDecimal salary;
        private BigDecimal incomeTax;
        private BigDecimal nationalCoalition;
        private BigDecimal healthInsurance;
        private BigDecimal employmentInsurance;
        private BigDecimal totalSalary;
        private String bankName;
        private String accountNumber;
        private boolean paymentStatus;
    }
}
