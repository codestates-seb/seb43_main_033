package main.main.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import main.main.companymember.entity.CompanyMember;
import main.main.salarystatement.entity.SalaryStatement;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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



    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("company")
    private List<CompanyMember> companyMembers = new ArrayList<>();
    public void addCompanyMember(CompanyMember companyMember) { this.companyMembers.add(companyMember); }

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) { this.salaryStatements.add(salaryStatement); }


//    @OneToMany(mappedBy = "company")
//    private List<Member> members = new ArrayList<>();
//    public void addMember(Member member) { this.members.add(member); }
}