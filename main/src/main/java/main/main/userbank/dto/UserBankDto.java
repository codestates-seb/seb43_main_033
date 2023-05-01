package main.main.userbank.dto;


import lombok.Getter;
import lombok.Setter;

public class UserBankDto {
@Getter
@Setter
public static class Post {

    private Long userId;
    private Long bankId;
    private Long accountNumber;
}
@Getter
@Setter
public static class Patch {
    private Long userBankId;
    private Long userId;
    private Long bankId;
    private Long accountNumber;
}
@Getter
@Setter
public static class Response {

    private Long userBankId;
    private Long userId;
    private Long bankId;
    private Long accountNumber;

}

}
