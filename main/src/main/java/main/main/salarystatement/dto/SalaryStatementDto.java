package main.main.salarystatement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SalaryStatementDto {
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
        private String memberName;
        private int year;
        private int month;
        private double hourlyWage;
        private double basePay;
        private double overtimePay;
        private int overtimePayBasis;
        private double nightWorkAllowance;
        private int nightWorkAllowanceBasis;
        private double holidayWorkAllowance;
        private int holidayWorkAllowanceBasis;
        private double unpaidLeave;
        private double salary;
        private double incomeTax;
        private double nationalCoalition;
        private double healthInsurance;
        private double employmentInsurance;
        private double totalSalary;
        private long bankId;
        private String bankName;
        private String accountNumber;
    }
}
