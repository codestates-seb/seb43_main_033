package main.main.exception;

import lombok.Getter;


public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "이메일이 이미 있습니다"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    TAG_NOT_FOUND(404, "Tag not found"),
    ONLY_AUTHOR(403, "You don't have permission"),
    CAN_NOT_OVER_VACATION_COUNT(403, "적립된 휴가가 없습니다."),
    REQUEST_NOT_FOUND(404, "휴가 신청 내역을 찾을 수 없습니다."),
    UNAUTHORIZED(401,  "권한이 없습니다."),
    LABORCONTRACT_FOR_SALARY_NOT_FOUND(404, "해당 기간에 유효한 근로 계약서를 찾을 수 없습니다."),
    LABORCONTRACT_NOT_FOUND(404, "근로 계약서를 찾을 수 없습니다."),
    MAKE_LABORCONTRACT_FIRST(404, "입사한 회사의 계약서를 찾을 수 없습니다. 회사 관리자에게 문의해 근로계약서를 먼저 입력해 주세요."),
    SALARYSTATEMENT_NOT_FOUND(404, "급여 명세서를 찾을 수 없습니다."),
    STATUS_NOT_FOUND(404, "정보를 찾을 수 없습니다."),
    BANK_NOT_FOUND(404, "찾는 은행이 없습니다."),
    COMPANY_NOT_FOUND(404, "찾는 회사가 없습니다."),
    MEMBERBANK_NOT_FOUND(404, "계좌정보를 찾을 수 없습니다."),
    COMPANYMEMBER_NOT_FOUND(404, "회사에서 회원을 찾을 수 없습니다"),
    COMPANYMEMBER_EXISTS(409, "회사에 이미 가입된 회원입니다"),
    INVALID_STATUS(400, "잘못된 입력입니다."),
    IMAGE_NOT_SUPPORT(400, "지원하지 않는 이미지 형식입니다."),
    BUSINESS_NOT_FOUND(404, "등록된 사업자가 없습니다."),
    INVALID_BANK_UPDATE(999,"은행을 변경하려면 계좌 삭제 후 다시 진행해 주세요."),
    MEMBERBANK_ONLY_ONE(007,"적어도 하나의 계좌를 주계좌로 지정해야합니다."),
    MEMBERBANK_ACCOUNTNUM_EXISTS(409, "이미 존재하는 계좌입니다."),
    IMAGE_ALREADY_EXISTS(409, "이미 등록된 이미지가 있습니다."),
    IMAGE_NOT_FOUND(404, "이미지를 찾을 수 없습니다");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
