package main.main.statusofwork.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.statusofwork.entity.StatusOfWork;

import java.time.LocalDateTime;
import java.util.List;

public class StatusOfWorkDto {
    @Getter
    @Setter
    public static class Post {
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
        private StatusOfWork.Note note;
    }

    @Getter
    @Setter
    public static class WPost {
        private Long companyId;
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
        private StatusOfWork.Note note;
    }

    @Getter
    @Setter
    public static class Patch {
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
        private StatusOfWork.Note note;
    }

    @Getter
    @Builder
    public static class Response {
        private long id;
        private long memberId;
        private String memberName;
        private long companyId;
        private String companyName;
        private LocalDateTime startTime;
        private LocalDateTime finishTime;
        private String note;
    }

    @Getter
    @Builder
    public static class MyWork{
        private List<CompanyInfo> company;
        private List<Response> status;
    }

    @Getter
    @Builder
    public static class CompanyInfo {
        private Long companyId;
        private String companyName;
    }
}
