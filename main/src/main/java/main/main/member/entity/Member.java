package main.main.member.entity;

import lombok.*;
import main.main.company.entity.Company;
import main.main.member.dto.MemberDto;
import main.main.member.dto.Position;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.memberbank.entity.MemberBank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String residentNumber;
    private String grade;
    private String address;
    @Enumerated(EnumType.STRING)
    private Position position;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @ManyToOne
    private Company company;
    @OneToMany
    private List<SalaryStatement> salaryStatements = new ArrayList<>();
    public void addSalaryStatement(SalaryStatement salaryStatement) {
        this.salaryStatements.add(salaryStatement);
    }

    @OneToMany(mappedBy = "member")
    private List<MemberBank> memberBanks = new ArrayList<>();

}
