package main.main.salarystatement.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.calculationofsalary.entity.CalculationOfSalary;
import main.main.company.entity.Company;
import main.main.userbank.entity.UserBank;

import javax.persistence.*;
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
    @JoinColumn(name = "USER_BANK_ID")
    private UserBank userBank;

    private long salary;

    private boolean paymentStatus;

    @OneToMany(mappedBy = "salaryStatement")
    private List<CalculationOfSalary> calculationOfSalaries = new ArrayList<>();

    public void addCalculationOfSalary(CalculationOfSalary calculationOfSalary) {
        this.calculationOfSalaries.add(calculationOfSalary);
    }
}
