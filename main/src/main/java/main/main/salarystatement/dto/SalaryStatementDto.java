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
        private int hourlyWage;
        private int basePay;
        private int overtimePay;
        private int overtimePayBasis;
        private int nightWorkAllowance;
        private int nightWorkAllowanceBasis;
        private int holidayWorkAllowance;
        private int holidayWorkAllowanceBasis;
        private int unpaidLeave;
        private int salary;
        private int incomeTax;
        private int nationalCoalition;
        private int healthInsurance;
        private int employmentInsurance;
        private int totalSalary;
        private long bankId;
        private String bankName;
        private String accountNumber;
    }
}
