package main.main.laborcontract.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private long basicSalary;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private String information;

//    private File loborContractFile
}
