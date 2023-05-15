package main.main.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.bank.entity.Bank;

@Getter
public class BankDto {

    @Getter
    @Builder
    public static class Response {
    private Long bankId;
    private String bankCode;
    private String bankName;
    }

    @Getter
    @Builder
    public static class ResponseForList {
        private Long bankId;
        private String bankCode;
        private String bankName;
    }

    @Getter
    @Builder
    public static class BankToMember {
        private Long bankId;
        private String bankCode;
        private String bankName;

    }
}
