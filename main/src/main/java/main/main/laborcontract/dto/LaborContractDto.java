package main.main.laborcontract.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.salarystatement.dto.PreDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LaborContractDto {
    @Getter
    @Setter
    public static class Post {
        private long companyMemberId;
        private long companyId;
        private BigDecimal basicSalary;
        private LocalDate startOfContract;
        private LocalDate endOfContract;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Setter
    public static class Patch {
        private BigDecimal basicSalary;
        private LocalDate startOfContract;
        private LocalDate endOfContract;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Builder
    public static class Response {
        private long laborContactId;
        private String memberName;
        private String companyName;
        private BigDecimal basicSalary;
        private LocalDate startOfContract;
        private LocalDate endOfContract;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
        private String uri;
    }
}
