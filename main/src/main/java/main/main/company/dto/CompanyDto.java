package main.main.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.companymember.dto.CompanyMemberDto;

import java.math.BigDecimal;
import java.util.List;

public class CompanyDto {
    @Getter
    @Setter
    public static class Post {

        private String companyName;
        private String companySize;
        private String businessNumber;
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
        private Long memberId;
        private String companyName;
        private String companySize;
        private String businessNumber;
        private String address;
        private String information;
        private List<CompanyMemberDto.ResponseForList> companyMembers;
        private BigDecimal theSalaryOfTheCompanyThisMonth;
        private BigDecimal theSalaryOfTheCompanyLastMonth;


    }


    @Getter
    @Setter
    @Builder
    public static class ResponseForList {

        private Long companyId;
        private Long memberId;
        private String companyName;
        private String companySize;
        private String businessNumber;
        private String address;
        private String information;
        private List<CompanyMemberDto.ResponseForList> companyMembers;


    }


}