package main.main.companymember.entity;

import lombok.*;
import main.main.company.entity.Company;
import main.main.companymember.dto.Status;
import main.main.member.entity.Member;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long companyMemberId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String Grade;
    private String Team;

    private Status status;

    public void setCompany(Company company) {
        this.company = company;
        if(!company.getCompanyMembers().contains(this)) {
            company.getCompanyMembers().add(this);
        }
    }
}
