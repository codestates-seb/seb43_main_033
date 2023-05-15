package main.main.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {


    @Getter
    private ExceptionCode exceptionCode;
    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }


    private String message;
    public BusinessLogicException(ExceptionCode exceptionCode, String message) {
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

    public ExceptionCode getCode() {
        return exceptionCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


