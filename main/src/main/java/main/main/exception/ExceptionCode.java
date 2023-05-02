package main.main.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_RESIDENTNUMBER_EXISTS(409, "residentNumber exists"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    TAG_NOT_FOUND(404, "Tag not found"),
    ONLY_AUTHOR(403, "You don't have permission"),
    UNAUTHORIZED(401,  "Unauthorized"),
    LABORCONTRACT_NOT_FOUND(404, "근로 계약서를 찾을 수 없습니다."),
    COMPANY_NOT_FOUND(404, "Company not found");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
