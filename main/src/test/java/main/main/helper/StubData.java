package main.main.helper;

import main.main.auth.jwt.JwtTokenizer;
import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.memberbank.dto.MemberBankDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class StubData {
    public static class MockSecurity {
        public static String getValidAccessToken(String secretKey) {
            JwtTokenizer jwtTokenizer = new JwtTokenizer();
            Map<String, Object> claims = new HashMap<>();
            claims.put("memberId", 1L);

            String subject = "test access token";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 1);
            Date expiration = calendar.getTime();

            String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

            String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

            return accessToken;
        }
    }

    public static class MockLaborContract {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            LocalDateTime time = LocalDateTime.now();

            LaborContractDto.Post post = new LaborContractDto.Post();
            post.setMemberId(1L);
            post.setCompanyId(1L);
            post.setBasicSalary(3000000L);
            post.setStartOfContract(time);
            post.setEndOfContract(time);
            post.setStartTime(LocalTime.MIDNIGHT);
            post.setFinishTime(LocalTime.MIDNIGHT);
            post.setInformation("근로계약서 정보");

            LaborContractDto.Patch patch = new LaborContractDto.Patch();
            patch.setBasicSalary(3200000);
            patch.setStartTime(LocalTime.MIDNIGHT);
            patch.setFinishTime(LocalTime.MIDNIGHT);
            patch.setInformation("수정된 근로계약서 정보");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static LaborContractDto.Response getResponseBody() {
            return LaborContractDto.Response.builder()
                    .memberName("직원 이름")
                    .companyName("회사 이름")
                    .basicSalary(3000000)
                    .startTime(LocalTime.MIDNIGHT)
                    .finishTime(LocalTime.MIDNIGHT)
                    .information("근로계약서 정보").build();
        }

    }


    public static class MockCompany {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {

            CompanyDto.Post post = new CompanyDto.Post();
            post.setCompanyName("회사명");
            post.setCompanySize("회사 규모");
            post.setBusinessNumber(1L);
            post.setAddress("회사 주소");
            post.setInformation("회사 정보");

            CompanyDto.Patch patch = new CompanyDto.Patch();
            patch.setCompanyId(1L);
            patch.setCompanyName("회사명");
            patch.setCompanySize("회사 규모");
            patch.setBusinessNumber(1L);
            patch.setAddress("회사 주소");
            patch.setInformation("회사 정보");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }

        public static Page<Company> getCompaniesByPage() {
            Company company1 = new Company();
            Company company2 = new Company();

            return new PageImpl<>(List.of(company1, company2), PageRequest.of(0, 5, Sort.by("companyId").descending()),2);

        }

        public static CompanyDto.Response getCompanyResponse() {
            return CompanyDto.Response.builder()
                    .companyId(1L)
                    .companyName("회사명")
                    .memberId(1L)
                    .companySize("회사 규모")
                    .businessNumber(1L)
                    .address("회사 주소")
                    .information("회사 정보")
                    .build();
        }

        public static List<CompanyDto.Response> getCompanyResponseForList() {
            return List.of(
                    CompanyDto.Response.builder()
                            .companyId(1L)
                            .companyName("회사명")
                            .memberId(1L)
                            .companySize("회사 규모")
                            .businessNumber(1L)
                            .address("회사 주소")
                            .information("회사 정보")
                            .build(),
                    CompanyDto.Response.builder()
                            .companyId(2L)
                            .companyName("회사명")
                            .memberId(2L)
                            .companySize("회사 규모")
                            .businessNumber(2L)
                            .address("회사 주소")
                            .information("회사 정보")
                            .build()
            );
        }
    }


    public static class MockMemberBank {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {

            MemberBankDto.Post post = new MemberBankDto.Post();
            post.setMemberId(1L);
            post.setBankId(1L);
            post.setAccountNumber("회원 계좌 번호");

            MemberBankDto.Patch patch = new MemberBankDto.Patch();
            patch.setMemberBankId(1L);
            patch.setMemberId(1L);
            patch.setBankId(1L);
            patch.setAccountNumber("회원 계좌 번호");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }


        public static MemberBankDto.Response getMemberBankResponse() {
            return MemberBankDto.Response.builder()
                    .memberBankId(1L)
                    .memberId(1L)
                    .bankId(1L)
                    .bankName("회원 계좌 은행명")
                    .accountNumber("회원 계좌 번호")
                    .build();
        }
    }


    public static class MockBank {



        public static BankDto.Response getBankResponse() {
            return BankDto.Response.builder()
                    .bankId(1L)
                    .bankName("회원 계좌 은행명")
                    .build();
        }
    }
}
