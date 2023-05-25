package main.main.helper;

import main.main.auth.jwt.JwtTokenizer;
import main.main.bank.dto.BankDto;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
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
            LocalDate time = LocalDate.now();

            LaborContractDto.Post post = new LaborContractDto.Post();
            post.setCompanyMemberId(1L);
            post.setCompanyId(1L);
            post.setBasicSalary(BigDecimal.valueOf(3000000));
            post.setStartOfContract(time);
            post.setEndOfContract(time);
            post.setStartTime(LocalTime.MIDNIGHT);
            post.setFinishTime(LocalTime.MIDNIGHT);
            post.setInformation("근로계약서 정보");

            LaborContractDto.Patch patch = new LaborContractDto.Patch();
            patch.setBasicSalary(BigDecimal.valueOf(3200000));
            patch.setStartOfContract(LocalDate.now());
            patch.setEndOfContract(LocalDate.now());
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
            LocalDate time = LocalDate.now();
            return LaborContractDto.Response.builder()
                    .laborContactId(1L)
                    .memberName("직원 이름")
                    .companyName("회사 이름")
                    .bankName("은행 이름")
                    .accountNumber("계좌 번호")
                    .accountHolder("예금주")
                    .basicSalary(BigDecimal.valueOf(3000000))
                    .startOfContract(time)
                    .endOfContract(time)
                    .startTime(LocalTime.MIDNIGHT)
                    .finishTime(LocalTime.MIDNIGHT)
                    .information("근로계약서 정보")
                    .uri("파일uri").build();
        }

        public static List<LaborContractDto.Response> getMultiResponseBody() {
            LocalDate time = LocalDate.now();
            return List.of(
                    LaborContractDto.Response.builder()
                            .laborContactId(2L)
                            .memberName("직원 이름")
                            .companyName("회사 이름")
                            .bankName("은행 이름")
                            .accountNumber("계좌 번호")
                            .accountHolder("예금주")
                            .basicSalary(BigDecimal.valueOf(3000000))
                            .startOfContract(time)
                            .endOfContract(time)
                            .startTime(LocalTime.MIDNIGHT)
                            .finishTime(LocalTime.MIDNIGHT)
                            .information("근로계약서 정보")
                            .uri("파일uri").build(),
                    LaborContractDto.Response.builder()
                            .laborContactId(1L)
                            .memberName("직원 이름")
                            .companyName("회사 이름")
                            .bankName("은행 이름")
                            .accountNumber("계좌 번호")
                            .accountHolder("예금주")
                            .basicSalary(BigDecimal.valueOf(3000000))
                            .startOfContract(time)
                            .endOfContract(time)
                            .startTime(LocalTime.MIDNIGHT)
                            .finishTime(LocalTime.MIDNIGHT)
                            .information("근로계약서 정보")
                            .uri("파일uri").build()
            );
        }

        public static HashMap<byte[], String> getImage() throws IOException {
            InputStream inputStream = new FileInputStream("mock/image.png");
            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            HashMap<byte[], String> imageMap = new HashMap<>();
            imageMap.put(imageByteArray, "png");
            return imageMap;
        }
    }

    public static class MockStatusOfWork {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            LocalDateTime time = LocalDateTime.now();

            StatusOfWorkDto.Post post = new StatusOfWorkDto.Post();
            post.setStartTime(time);
            post.setFinishTime(time);
            post.setNote(StatusOfWork.Note.지각);

            StatusOfWorkDto.Patch patch = new StatusOfWorkDto.Patch();
            patch.setStartTime(time);
            patch.setFinishTime(time);
            patch.setNote(StatusOfWork.Note.결근);

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

        public static List<StatusOfWorkDto.Response> getResponsesBody() {
            LocalDateTime time = LocalDateTime.now();

            return List.of(StatusOfWorkDto.Response.builder()
                            .id(2L)
                            .memberId(1L)
                            .memberName("직원 이름")
                            .companyId(1L)
                            .companyName("회사 이름")
                            .startTime(time)
                            .finishTime(time)
                            .note("지각").build(),
                    StatusOfWorkDto.Response.builder()
                            .id(1L)
                            .memberId(1L)
                            .memberName("직원 이름")
                            .companyId(1L)
                            .companyName("회사 이름")
                            .startTime(time)
                            .finishTime(time)
                            .note("지각").build()
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

        public static StatusOfWorkDto.MyWork getMultiResponseBody() {
            LocalDateTime time = LocalDateTime.now();

            return StatusOfWorkDto.MyWork.builder()
                    .company(List.of(
                            StatusOfWorkDto.CompanyInfo.builder()
                                    .companyId(1L)
                                    .companyName("회사 이름").build(),
                            StatusOfWorkDto.CompanyInfo.builder()
                                    .companyId(2L)
                                    .companyName("회사 이름").build()
                    ))
                    .status(List.of(
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
                                    .note("지각").build())).build();
        }

        public static List<StatusOfWorkDto.Today> getTodayList() {
            return List.of(
                    StatusOfWorkDto.Today.builder()
                            .member(StubData.MockCompanyMember.getCompanyMemberResponse())
                            .status(getResponsesBody()).build()
            );
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
                    .year(2023)
                    .month(1)
                    .name("직원 이름")
                    .team("직원 부서명")
                    .grade("직원 직급명")
                    .hourlyWage(BigDecimal.valueOf(10000))
                    .basePay(BigDecimal.valueOf(2090000))
                    .overtimePay(BigDecimal.valueOf(0))
                    .overtimePayBasis(0)
                    .nightWorkAllowance(BigDecimal.valueOf(0))
                    .nightWorkAllowanceBasis(0)
                    .holidayWorkAllowance(BigDecimal.valueOf(0))
                    .holidayWorkAllowanceBasis(0)
                    .unpaidLeave(BigDecimal.valueOf(0))
                    .salary(BigDecimal.valueOf(2090000))
                    .incomeTax(BigDecimal.valueOf(10000))
                    .nationalCoalition(BigDecimal.valueOf(10000))
                    .healthInsurance(BigDecimal.valueOf(10000))
                    .employmentInsurance(BigDecimal.valueOf(10000))
                    .totalSalary(BigDecimal.valueOf(2050000))
                    .bankName("국민")
                    .accountNumber("111-111111-11-11")
                    .build();
        }

        public static List<SalaryStatementDto.Response> getMultiResponseBody() {
            return List.of(
                    SalaryStatementDto.Response.builder()
                            .id(2L)
                            .companyId(1L)
                            .companyName("회사 이름")
                            .memberId(1L)
                            .year(2023)
                            .month(2)
                            .name("직원 이름")
                            .team("직원 부서명")
                            .grade("직원 직급명")
                            .hourlyWage(BigDecimal.valueOf(10000))
                            .basePay(BigDecimal.valueOf(2090000))
                            .overtimePay(BigDecimal.valueOf(0))
                            .overtimePayBasis(0)
                            .nightWorkAllowance(BigDecimal.valueOf(0))
                            .nightWorkAllowanceBasis(0)
                            .holidayWorkAllowance(BigDecimal.valueOf(0))
                            .holidayWorkAllowanceBasis(0)
                            .unpaidLeave(BigDecimal.valueOf(0))
                            .salary(BigDecimal.valueOf(2090000))
                            .incomeTax(BigDecimal.valueOf(10000))
                            .nationalCoalition(BigDecimal.valueOf(10000))
                            .healthInsurance(BigDecimal.valueOf(10000))
                            .employmentInsurance(BigDecimal.valueOf(10000))
                            .totalSalary(BigDecimal.valueOf(2050000))
                            .bankName("국민")
                            .accountNumber("111-111111-11-11")
                            .build(),
                    SalaryStatementDto.Response.builder()
                            .id(1L)
                            .companyId(1L)
                            .companyName("회사 이름")
                            .memberId(1L)
                            .year(2023)
                            .month(1)
                            .name("직원 이름")
                            .team("직원 부서명")
                            .grade("직원 직급명")
                            .hourlyWage(BigDecimal.valueOf(10000))
                            .basePay(BigDecimal.valueOf(2090000))
                            .overtimePay(BigDecimal.valueOf(0))
                            .overtimePayBasis(0)
                            .nightWorkAllowance(BigDecimal.valueOf(0))
                            .nightWorkAllowanceBasis(0)
                            .holidayWorkAllowance(BigDecimal.valueOf(0))
                            .holidayWorkAllowanceBasis(0)
                            .unpaidLeave(BigDecimal.valueOf(0))
                            .salary(BigDecimal.valueOf(2090000))
                            .incomeTax(BigDecimal.valueOf(10000))
                            .nationalCoalition(BigDecimal.valueOf(10000))
                            .healthInsurance(BigDecimal.valueOf(10000))
                            .employmentInsurance(BigDecimal.valueOf(10000))
                            .totalSalary(BigDecimal.valueOf(2050000))
                            .bankName("국민")
                            .accountNumber("111-111111-11-11")
                            .build()
            );
        }
    }


    public static class MockCompany {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {

            CompanyDto.Post post = new CompanyDto.Post();
            post.setCompanyName("회사명");
            post.setCompanySize("회사 규모");
            post.setBusinessNumber("사업자 등록 번호");
            post.setAddress("회사 주소");
            post.setInformation("회사 정보");

            CompanyDto.Patch patch = new CompanyDto.Patch();
            patch.setCompanyId(1L);
            patch.setCompanyName("회사명");
            patch.setCompanySize("회사 규모");
            patch.setBusinessNumber("사업자 등록 번호");
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
                    .companySize("회사 규모")
                    .businessNumber("사업자 등록 번호")
                    .address("회사 주소")
                    .information("회사 정보")
                    .companyMembers(MockCompanyMember.getCompanyMembersToCompanyMembersResponse())
                    .theSalaryOfTheCompanyLastMonth(BigDecimal.valueOf(100000))
                    .theSalaryOfTheCompanyThisMonth(BigDecimal.valueOf(200000))
                    .build();
        }

//        public static CompanyDto.ResponseForSalary getCompanyResponseForSalary() {
//
//            return CompanyDto.ResponseForSalary.builder()
//                    .companyId(1L)
//                    .companyName("회사명")
//                    .totalSalaryOfCompany(BigDecimal.valueOf(10000))
//                    .build();
//        }

        public static List<CompanyDto.ResponseForList> getCompaniesToCompaniesResponse() {
            return List.of(
                    CompanyDto.ResponseForList.builder()
                            .companyId(1L)
                            .companyName("회사명")
                            .companySize("회사 규모")
                            .businessNumber("사업자 등록 번호")
                            .address("회사 주소")
                            .information("회사 정보")
                            .companyMembers(MockCompanyMember.getCompanyMembersToCompanyMembersResponse())
                            .build(),
                    CompanyDto.ResponseForList.builder()
                            .companyId(2L)
                            .companyName("회사명")
                            .companySize("회사 규모")
                            .businessNumber("사업자 등록 번호")
                            .address("회사 주소")
                            .information("회사 정보")
                            .companyMembers(MockCompanyMember.getCompanyMembersToCompanyMembersResponse())
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
            post.setMainAccount(true);

            MemberBankDto.Patch patch = new MemberBankDto.Patch();
            patch.setMemberBankId(1L);
            patch.setMemberId(1L);
            patch.setBankId(1L);
            patch.setAccountNumber("회원 계좌 번호");
            patch.setMainAccount(true);

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static List<MemberBank> memberBanks() {
            return Arrays.asList(new MemberBank(), new MemberBank());
        }


        public static Page<MemberBank> getMemberBanksByPage() {
            MemberBank memberBank1 = new MemberBank();
            MemberBank memberBank2 = new MemberBank();

            return new PageImpl<>(List.of(memberBank1, memberBank2), PageRequest.of(0, 5, Sort.by("memberBankId").descending()), 2);
        }

        public static MemberBankDto.Response getMemberBankResponse() {
            return MemberBankDto.Response.builder()
                    .memberBankId(1L)
                    .memberId(1L)
                    .bankId(1L)
                    .bankCode("회원 계좌 은행코드")
                    .bankName("회원 계좌 은행명")
                    .accountNumber("회원 계좌 번호")
                    .mainAccount(true)
                    .build();
        }

        public static List<MemberBankDto.ResponseForList> getmemberBanksToMemberBanksResponse() {
            return List.of(
                    MemberBankDto.ResponseForList.builder()
                            .memberBankId(1L)
                            .memberId(1L)
                            .bankId(1L)
                            .bankCode("회원 계좌 은행코드")
                            .bankName("회원 계좌 은행명")
                            .accountNumber("회원 계좌 번호")
                            .mainAccount(true)
                            .build(),
                    MemberBankDto.ResponseForList.builder()
                            .memberBankId(1L)
                            .memberId(1L)
                            .bankId(1L)
                            .bankCode("회원 계좌 은행코드")
                            .bankName("회원 계좌 은행명")
                            .accountNumber("회원 계좌 번호")
                            .mainAccount(true)
                            .build()
            );
        }
    }


    public static class MockBank {

        public static BankDto.Response getBankResponse() {
            return BankDto.Response.builder()
                    .bankId(1L)
//                    .bankCode("회원 계좌 은행 코드")
                    .bankName("회원 계좌 은행명")
                    .build();
        }
    }


    public static class MockCompanyMember {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            CompanyMemberDto.Post post = new CompanyMemberDto.Post();
            post.setCompanyId(1L);
            post.setMemberId(1L);
            post.setGrade("사원 직급");
            post.setTeam("사원 소속 부서");

            CompanyMemberDto.Patch patch = new CompanyMemberDto.Patch();
            patch.setCompanyMemberId(1L);
            patch.setCompanyId(1L);
            patch.setMemberId(1L);
            patch.setGrade("사원 직급");
            patch.setTeam("사원 소속 부서");

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, post);
            stubRequestBody.put(HttpMethod.PATCH, patch);
        }

        public static Object getRequestBody(HttpMethod method) { return stubRequestBody.get(method); }

        public static Page<CompanyMember> getCompanyMembersByPage() {
            CompanyMember companyMember1 = new CompanyMember();
            CompanyMember companyMember2 = new CompanyMember();

            return new PageImpl<>(List.of(companyMember1, companyMember2), PageRequest.of(0, 5, Sort.by("companyMemberId").descending()),2);

        }

        public static CompanyMemberDto.Response getCompanyMemberResponse() {
            return CompanyMemberDto.Response.builder()
                    .companyMemberId(1L)
                    .companyId(1L)
                    .memberId(1L)
                    .name("test")
                    .grade("사원 직급")
                    .team("사원 소속 부서")
                    .status(Status.PENDING)
                    .build();
        }

        public static List<CompanyMemberDto.ResponseForList> getCompanyMembersToCompanyMembersResponse() {
            return List.of(
                    CompanyMemberDto.ResponseForList.builder()
                            .companyMemberId(1L)
                            .companyId(1L)
                            .memberId(1L)
                            .grade("사원 직급")
                            .team("사원 소속 부서")
                            .status(Status.PENDING)
                            .build(),
                    CompanyMemberDto.ResponseForList.builder()
                            .companyMemberId(2L)
                            .companyId(1L)
                            .memberId(1L)
                            .grade("사원 직급")
                            .team("사원 소속 부서")
                            .status(Status.PENDING)
                            .build()
            );
        }
    }
}
