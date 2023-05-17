package main.main.companymember.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CompanyMemberDto {

    @Getter
    @Setter
    public static class Post {

        private Long companyId;
        private Long memberId;
        private String grade;
        private String team;
        private Authority authority;


    }

    @Getter
    @Setter
    public static class Patch {

        private Long companyMemberId;
        private Long companyId;
        private Long memberId;
        private String grade;
        private String team;
        private List<String> roles;
        private Authority authority;


    }


    @Getter
    @Setter
    @Builder
    public static class Response {

        private Long companyMemberId;
        private Long companyId;
        private Long memberId;
        private String grade;
        private String team;
        private Status status;
        private List<String> roles;
        private Authority authority;


    }

    @Getter
    @Setter
    @Builder
    public static class ResponseForList {

        private Long companyMemberId;
        private Long companyId;
        private Long memberId;
        private String grade;
        private String team;
        private Status status;
        private List<String> roles;
        private Authority authority;


    }
}
