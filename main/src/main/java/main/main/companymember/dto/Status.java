package main.main.companymember.dto;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("대기"),
    APPROVED("승인"),

    REFUSE("거절");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
