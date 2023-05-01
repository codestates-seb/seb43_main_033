package main.main.laborcontract.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.user.entity.User;

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
    @JoinColumn(name = "USER_ID")
    private User user;

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
