package main.main.salarystatement.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PreDto {
    @Getter
    @Builder
    public static class Member {
        private long companyMemberId;
        private String name;
        private String bankName;
        private String accountNumber;
        private String accountHolder;
    }

    @Getter
    @Builder
    public static class Status {
        private long statusId;
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
        private String note;
    }
    @Getter
    @Builder
    public static class Statement {
        private int year;
        private int month;
        private BigDecimal basePay;
        private BigDecimal overtimePay;
        private BigDecimal nightWorkAllowance;
        private BigDecimal holidayWorkAllowance;
        private BigDecimal unpaidLeave;
        private BigDecimal salary;
        private BigDecimal incomeTax;
        private BigDecimal nationalCoalition;
        private BigDecimal healthInsurance;
        private BigDecimal employmentInsurance;
        private BigDecimal deduction;
        private BigDecimal totalSalary;
    }
}
