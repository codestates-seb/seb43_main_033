package main.main.bank.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.bank.entity.Bank;

import java.util.List;

@Getter
public class BankDto {

    @Getter
    @Setter
    public static class Post {

//        private Long bankId;
//        private String bankName;
        private Bank.BankGroup bankGroup;

    }

    @Getter
    @Builder
    public static class Response {
    private Long bankId;
    private String bankName;
    }

//
//    @Getter
//    @Builder
//    public static  class ResponseForList {
//        private Long bankId;
//        private String bankName;
//        private List<Response> bankList;
//    }
}
