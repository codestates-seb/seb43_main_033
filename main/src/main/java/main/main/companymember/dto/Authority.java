package main.main.companymember.dto;

import lombok.Getter;
@Getter
public enum Authority {

    //모든 권한 접근
    SUPER("SUPER",0),
    //각 회사의 모든 권한 접근
    ADMIN("ADMIN", 1),
    //각 회사의 일부 권한 접근
    MEMBER("MEMBER", 2),
    //소속 회사 없음
    GUEST("GUEST", 3);

    private final String role;
    private final int priority;

    Authority(String role, int priority) {
        this.role = role;
        this.priority = priority;
    }

    public String getRole() {
        return role;
    }

    public int getPriority() {
        return priority;
    }
}

