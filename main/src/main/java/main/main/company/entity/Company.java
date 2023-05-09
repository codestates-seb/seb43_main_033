package main.main.company.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.companymember.entity.CompanyMember;
import main.main.salarystatement.entity.SalaryStatement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long companyId;

    private String companyName;
    private String companySize;
    private String businessNumber;
    private String address;
    private String information;
    private BigDecimal totalSalaryOfCompany;



    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<CompanyMember> companyMembers = new ArrayList<>();
    public void addCompanyMember(CompanyMember companyMember) { this.companyMembers.add(companyMember); }

    @OneToMany(mappedBy = "company")
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) { this.salaryStatements.add(salaryStatement); }
}