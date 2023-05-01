package main.main.company.dto;

import lombok.Getter;
import lombok.Setter;

public class CompanyDto {
    @Getter
    @Setter
    public static class Post {

        private Long userId;
        private String companySize;
        private Long businessNumber;
        private String address;
        private String information;
    }

    @Getter
    @Setter
    public static class Patch {

        private Long companyId;
        private Long userId;
        private String companySize;
        private Long businessNumber;
        private String address;
        private String information;
    }

    @Getter
    @Setter
    public static class Response {

        private Long companyId;
        private Long userId;
        private String companySize;
        private Long businessNumber;
        private String address;
        private String information;
    }
}
