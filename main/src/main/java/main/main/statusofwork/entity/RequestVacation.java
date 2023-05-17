package main.main.statusofwork.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class RequestVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "COMPANY_MEMBER_ID")
    private CompanyMember companyMember;

    @ManyToOne
    @JoinColumn(name = "VACATION_ID")
    private Vacation vacation;

    private LocalDate vacationStart;

    private LocalDate vacationEnd;

    private Status status = Status.PENDING;
}
