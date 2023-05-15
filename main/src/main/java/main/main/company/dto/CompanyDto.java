package main.main.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CompanyDto {
    @Getter
    @Setter
    public static class Post {

        private String companyName;
        private String companySize;
        private String address;
        private String information;
    }

    @Getter
    @Setter
    public static class Patch {

        private Long companyId;
        private String companyName;
        private String companySize;
        private String businessNumber;
        private String address;
        private String information;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private Long companyId;
        private String companyName;
        private String companySize;
        private String businessNumber;
        private String address;
        private String information;
        private BigDecimal theSalaryOfTheCompanyThisMonth;
        private BigDecimal theSalaryOfTheCompanyLastMonth;


    }


    @Getter
    @Setter
    @Builder
    public static class ResponseForList {

        private Long companyId;
        private String companyName;
        private String companySize;
        private String businessNumber;
        private String address;
        private String information;
        private BigDecimal theSalaryOfTheCompanyThisMonth;
        private BigDecimal theSalaryOfTheCompanyLastMonth;

    }

//    @Getter
//    @Setter
//    @Builder
//    public static class ResponseForSalary {
//
//        private Long companyId;
//        private String companyName;
//        private BigDecimal theSalaryOfTheCompanyThisMonth;
//        private BigDecimal theSalaryOfTheCompanyLastMonth;
//    }

}