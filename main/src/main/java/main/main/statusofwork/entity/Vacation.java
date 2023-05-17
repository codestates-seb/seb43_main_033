package main.main.statusofwork.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.companymember.entity.CompanyMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "COMPANY_MEMBER_ID")
    private CompanyMember companyMember;

    private int count;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.PERSIST)
    private List<RequestVacation> requestVacationList = new ArrayList<>();

    public void setCompanyMember(CompanyMember companyMember) {
        this.companyMember = companyMember;
        if (companyMember.getVacation() != this) {
            companyMember.setVacation(this);
        }
    }

    public void addRequest(RequestVacation requestVacation) {
        this.requestVacationList.add(requestVacation);
        if (requestVacation.getVacation() != this) {
            requestVacation.setVacation(this);
        }
    }
}
