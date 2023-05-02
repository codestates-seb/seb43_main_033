package main.main.statusofwork.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.calculationofsalary.entity.CalculationOfSalary;
import main.main.company.entity.Company;
import main.main.user.entity.Member;

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
    private Member member;

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
        EXTENDED_WORK(1.5), // 연장 근로
        HOLIDAY_WORK(1.5), // 휴일 근로 8시간 이내의 휴일근로: 통상 임금의 50% 이상 가산, 8시간 초과 휴일근로: 통상 임금의 100% 가산
        NIGHT_DUTY(1.5), // 야간 근로
        PAID_VACATION(1), // 유급 휴가
        UNPAID_LEAVE(-1); // 무급 휴가

        @Getter
        private double rate;

        note(double rate) {
            this.rate = rate;
        }
    }
}
