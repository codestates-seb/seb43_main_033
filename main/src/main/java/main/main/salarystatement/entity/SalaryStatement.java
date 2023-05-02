package main.main.salarystatement.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.calculationofsalary.entity.CalculationOfSalary;
import main.main.company.entity.Company;
import main.main.memberbank.entity.MemberBank;

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
    @JoinColumn(name = "MEMBER_BANK_ID")
    private MemberBank memberBank;

    private long salary;

    private boolean paymentStatus;

    @OneToMany(mappedBy = "salaryStatement")
    private List<CalculationOfSalary> calculationOfSalaries = new ArrayList<>();

    public void addCalculationOfSalary(CalculationOfSalary calculationOfSalary) {
        this.calculationOfSalaries.add(calculationOfSalary);
    }
}
