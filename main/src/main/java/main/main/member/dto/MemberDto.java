package main.main.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.memberbank.dto.MemberBankDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {
        @NotBlank(message = "이름은 필수 입력해야합니다.")
        private String name;
        private Long companyId;
        @NotBlank(message = "전화번호는 필수 입력해야합니다.")
        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
                message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
        private String phoneNumber;
        @NotBlank(message = "이메일은 필수 입력해야합니다.")
        @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}",
                message = "올바르지 않은 이메일 형식입니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력해야합니다.")
        private String password;
        @NotBlank(message = "주민번호는 필수 입력해야합니다.")
        private String birthday;
        @NotBlank(message = "주민번호는 필수 입력해야합니다.")
        private String residentNumber;
        private String address;

    }

    @Getter
    @Setter
    @Builder
    public static class Patch {
        private Long memberId;
        private Long companyId;
        private String name;
        private String phoneNumber;
        private String email;
        private String password;
        private String birthday;
        private String residentNumber;
        private String address;
    }

    @Getter
    @Builder
    public static class Response {
        private Long memberId;
        private String name;
        private String phoneNumber;
        private String email;
        private String birthday;
        private String residentNumber;
        private String address;
        private List<MemberBankDto.MemberBankList> bank;
        private List<CompanyMemberDto.CompanyMemberToMember> companyMembers;
    }
}
