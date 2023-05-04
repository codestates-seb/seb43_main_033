package main.main.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.member.entity.Member;

import java.nio.file.SimpleFileVisitor;
import java.util.List;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {
        private String name;
        private String phoneNumber;
        private String email;
        private String password;
        private String residentNumber;
        private String grade;
        private String address;

    }

    @Getter
    @Setter
    @Builder
    public static class Patch {
        private Long memberId;
        private Long companyId;
        private String name;
        private String phoneNumber;
        private String email;
        private String password;
        private String residentNumber;
        private String grade;
        private String address;
        private String position;
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
        private String position;
        private List<String> roles;
    }
}
