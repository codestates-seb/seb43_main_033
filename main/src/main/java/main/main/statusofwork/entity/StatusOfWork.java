package main.main.statusofwork.entity;

import lombok.Getter;
import lombok.Setter;
import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.salarystatement.entity.SalaryStatement;

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
    @JoinColumn(name = "SALARY_STATEMENT_ID")
    private SalaryStatement salaryStatement;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private note note;

    public enum note {
        지각(0, "지각"), // 지각
        조퇴(0, "조퇴"), // 조퇴
        결근(0, "결근"), // 결근
        연장근로(1.5, "연장 근로"), // 연장 근로
        휴일근로(1.5, "휴일 근로"), // 휴일 근로 8시간 이내의 휴일근로: 통상 임금의 50% 이상 가산, 8시간 초과 휴일근로: 통상 임금의 100% 가산
        야간근로(1.5, "야간 근로"), // 야간 근로
        유급휴가(0, "유급 휴가"), // 유급 휴가
        무급휴가(-1, "무급 휴가"); // 무급 휴가

        @Getter
        private double rate;
        @Getter
        private String status;

        note(double rate, String status) {
            this.rate = rate;
            this.status = status;
        }
    }
}
