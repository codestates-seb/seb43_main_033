package main.main.memberbank.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.bank.entity.Bank;

import java.util.List;

public class MemberBankDto {
@Getter
@Setter
public static class Post {

    private Long memberId;
    private Long bankId;
    private String accountNumber;
}
@Getter
@Setter
public static class Patch {
    private Long memberBankId;
    private Long memberId;
    private Long bankId;
    private String accountNumber;
}
@Getter
@Setter
@Builder
public static class Response {

    private Long memberBankId;
    private Long memberId;
    private Long bankId;
    private String bankCode;
    private String bankName;
    private String accountNumber;

}

    @Getter
    @Setter
    @Builder
    public static class ResponseForList {

        private Long memberBankId;
        private Long memberId;
        private Long bankId;
        private String bankCode;
        private String bankName;
        private String accountNumber;

    }

    @Getter
    @Builder
    public static class MemberBankList {

        private Long memberBankId;
        private Long bankId;
        private String bankName;
        private String accountNumber;
        private String bankCode;

    }
}
