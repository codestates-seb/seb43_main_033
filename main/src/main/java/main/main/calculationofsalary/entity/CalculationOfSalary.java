package main.main.calculationofsalary.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.statusofwork.entity.StatusOfWork;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CalculationOfSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long hourlyWage;

    @OneToMany(mappedBy = "calculationOfSalary")
    private List<StatusOfWork> statusOfWorks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "SALARY_STATEMENT")
    private SalaryStatement salaryStatement;

    public void addStatusOfWork(StatusOfWork statusOfWork) {
        this.statusOfWorks.add(statusOfWork);
    }
}
