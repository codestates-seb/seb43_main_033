package main.main.laborcontract.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.salarystatement.entity.SalaryStatement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class LaborContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "SALARY_STATEMENT_ID")
    private SalaryStatement salaryStatement;

    private LocalDateTime startOfContract;

    private LocalDateTime endOfContract;

    private BigDecimal basicSalary;

    private LocalTime startTime;

    private LocalTime finishTime;

    private String information;

//    private File loborContractFile
}
