package main.main.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "이메일이 이미 있습니다"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    TAG_NOT_FOUND(404, "Tag not found"),
    ONLY_AUTHOR(403, "You don't have permission"),
    UNAUTHORIZED(401,  "Unauthorized"),
    LABORCONTRACT_FOR_SALARY_NOT_FOUND(404, "해당 기간에 유효한 근로 계약서를 찾을 수 없습니다."),
    LABORCONTRACT_NOT_FOUND(404, "근로 계약서를 찾을 수 없습니다."),
    SALARYSTATEMENT_NOT_FOUND(404, "급여 명세서를 찾을 수 없습니다."),
    BANK_NOT_FOUND(404, "찾는 은행이 없습니다."),
    COMPANY_NOT_FOUND(404, "찾는 회사가 없습니다."),
    MEMBERBANK_NOT_FOUND(404, "계좌정보를 찾을 수 없습니다.");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
