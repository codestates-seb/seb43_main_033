package main.main.statusofwork.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.calculationofsalary.entity.CalculationOfSalary;
import main.main.company.entity.Company;
import main.main.employer.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StatusOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CALCULATION_OF_SALARY_ID")
    private CalculationOfSalary calculationOfSalary;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private String note;

    public enum note {
        LATE(0), // 지각
        EARLY_LEAVE(0), // 조퇴
        ABSENCE(0), // 결근
        OVERTIME(1.5), // 초과 근무
        PAID_VACATION(1), // 유급 휴가
        UNPAID_LEAVE(0); // 무급 휴가

        @Getter
        private double rate;

        note(double rate) {
            this.rate = rate;
        }
    }
}
