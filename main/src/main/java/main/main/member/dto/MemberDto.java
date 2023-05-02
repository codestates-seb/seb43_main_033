package main.main.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {
        private String name;
        private String phoneNumber;
        private String email;
        private String residentNumber;
        private String grade;
        private String address;

    }

    @Getter
    @Setter
    public static class Patch {
        private Long memberId;
        private Long companyId;
        private String name;
        private String phoneNumber;
        private String email;
        private String residentNumber;
        private String grade;
        private String address;
    }

    @Getter
    @Builder
    public static class Response {
        private Long memberId;
        private String name;
        private String phoneNumber;
        private String email;
        private String residentNumber;
        private String grade;
        private String address;

    }

}
