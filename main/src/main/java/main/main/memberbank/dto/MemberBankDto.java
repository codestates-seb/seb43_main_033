package main.main.memberbank.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberBankDto {
@Getter
@Setter
public static class Post {

    private Long memberId;
    private Long bankId;
    private String bankName;
    private Long accountNumber;
}
@Getter
@Setter
public static class Patch {
    private Long memberBankId;
    private Long memberId;
    private Long bankId;
    private String bankName;
    private Long accountNumber;
}
@Getter
@Setter
@Builder
public static class Response {

    private Long memberBankId;
    private Long memberId;
    private Long bankId;
    private String bankName;
    private Long accountNumber;

}

}
