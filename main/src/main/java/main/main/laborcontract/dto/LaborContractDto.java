package main.main.laborcontract.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LaborContractDto {
    @Getter
    @Setter
    public static class Post {
        private long memberId;
        private long companyId;
        private long basicSalary;
        private LocalDateTime startOfContract;
        private LocalDateTime endOfContract;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Setter
    public static class Patch {
        private double basicSalary;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }

    @Getter
    @Builder
    public static class Response {
        private String memberName;
        private String companyName;
        private double basicSalary;
        private LocalTime startTime;
        private LocalTime finishTime;
        private String information;
    }
}
