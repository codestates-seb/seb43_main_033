package main.main.member.dto;

import lombok.Getter;

@Getter
public enum Position {


        ADMIN("ADMIN"),
        MANAGER("MANAGER"),
        STAFF("STAFF");

        private final String role;


        Position(String role) {
            this.role = role;
        }

        public String getRole() {
                return role;
        }
}
