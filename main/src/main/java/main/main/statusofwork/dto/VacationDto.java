package main.main.statusofwork.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class VacationDto {
    @Getter
    @Setter
    public static class Post {
        private long companyId;
        private LocalDate vacationStart;
        private LocalDate vacationEnd;
    }

    @Getter
    @Builder
    public static class Response {
        private long requestId;
        private long companyMemberId;
        private String name;
        private LocalDate vacationStart;
        private LocalDate vacationEnd;
    }
}
