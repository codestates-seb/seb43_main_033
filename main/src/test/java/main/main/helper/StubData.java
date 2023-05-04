package main.main.helper;

import main.main.auth.jwt.JwtTokenizer;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
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

    public static class MockStatusOfWork {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            LocalDateTime time = LocalDateTime.now();

            StatusOfWorkDto.Post post = new StatusOfWorkDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setStartTime(time);
            post.setFinishTime(time);
            post.setNote(StatusOfWork.note.지각);

            StatusOfWorkDto.Patch patch = new StatusOfWorkDto.Patch();
            patch.setStartTime(time);
            patch.setFinishTime(time);
            patch.setNote(StatusOfWork.note.결근);

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static List<StatusOfWork> getStatusOfWorkList() {
            return List.of(
                    new StatusOfWork(),
                    new StatusOfWork()
            );
        }

        public static StatusOfWorkDto.Response getResponseBody() {
            LocalDateTime time = LocalDateTime.now();

            return StatusOfWorkDto.Response.builder()
                    .id(1L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build();
        }

        public static List<StatusOfWorkDto.Response> getMultiResponseBody() {
            LocalDateTime time = LocalDateTime.now();

            return List.of(
                    StatusOfWorkDto.Response.builder()
                    .id(1L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build(),
                    StatusOfWorkDto.Response.builder()
                    .id(2L)
                    .memberId(1L)
                    .memberName("직원 이름")
                    .companyId(1L)
                    .companyName("회사 이름")
                    .startTime(time)
                    .finishTime(time)
                    .note("지각").build());
        }
    }
    public static class MockSalaryStatement {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            SalaryStatementDto.Post post = new SalaryStatementDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setYear(2023);
            post.setMonth(1);

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static SalaryStatementDto.Response getResponseBody() {
            return SalaryStatementDto.Response.builder()
                    .id(1L)
                    .companyId(1L)
                    .companyName("회사 이름")
                    .memberId(1L)
                    .memberName("직원 이름")
                    .year(2023)
                    .month(1)
                    .hourlyWage(10000)
                    .basePay(2090000)
                    .overtimePay(0)
                    .overtimePayBasis(0)
                    .nightWorkAllowance(0)
                    .nightWorkAllowanceBasis(0)
                    .holidayWorkAllowance(0)
                    .holidayWorkAllowanceBasis(0)
                    .unpaidLeave(0)
                    .salary(2090000)
                    .incomeTax(10000)
                    .nationalCoalition(10000)
                    .healthInsurance(10000)
                    .employmentInsurance(10000)
                    .totalSalary(2050000)
                    .bankId(1L)
                    .bankName("국민")
                    .accountNumber("111-111111-11-11")
                    .build();
        }
    }
}
