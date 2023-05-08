package main.main.salarystatement.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.memberbank.entity.MemberBank;
import main.main.statusofwork.entity.StatusOfWork;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class SalaryStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "MEMBER_BANK_ID")
    private MemberBank memberBank;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private int year;

    private int month;

    private BigDecimal hourlyWage;

    private BigDecimal basePay; // 기본급

    private BigDecimal overtimePay; // 연장근로수당

    private int overtimePayBasis;

    private BigDecimal nightWorkAllowance; // 야간근로수당

    private int nightWorkAllowanceBasis;

    private BigDecimal holidayWorkAllowance; // 휴일근로수당

    private int holidayWorkAllowanceBasis;

    private BigDecimal unpaidLeave; // 무급휴가

    private BigDecimal salary;
    public void setSalary() {
        this.salary.add(basePay).add(overtimePay).add(nightWorkAllowance).add(holidayWorkAllowance).add(unpaidLeave);
    }

    private BigDecimal incomeTax;

    private BigDecimal nationalCoalition; // 국민 연금

    private BigDecimal healthInsurance; // 건강 보험

    private BigDecimal employmentInsurance; // 고용 보험

    private BigDecimal totalSalary;
    public void setTotalSalary() {
        this.totalSalary.subtract(salary).subtract(incomeTax).subtract(nationalCoalition).subtract(healthInsurance).subtract(employmentInsurance);
    }

    private boolean paymentStatus;

    @OneToMany(mappedBy = "salaryStatement")
    private List<StatusOfWork> statusOfWorks = new ArrayList<>();

    public void addCalculationOfSalary(StatusOfWork statusOfWork) {
        this.statusOfWorks.add(statusOfWork);
    }
}
