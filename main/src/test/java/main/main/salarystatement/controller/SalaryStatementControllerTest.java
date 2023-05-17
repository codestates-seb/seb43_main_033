package main.main.salarystatement.controller;

import main.main.auth.jwt.JwtTokenizer;
import main.main.helper.SalaryStatementHelper;
import main.main.helper.StubData;
import main.main.salarystatement.dto.PreDto;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalaryStatementController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SalaryStatementControllerTest implements SalaryStatementHelper {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private SalaryStatementService salaryStatementService;
    @MockBean
    private SalaryStatementMapper salaryStatementMapper;
    private String accessToken;
    @BeforeAll
    public void init() {
        System.out.println("# BeforeAll");
        accessToken = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }

    @Test
    @DisplayName("PreSalaryStatement Get Test")
    public void preSalaryStatement() throws Exception {
        String year = "2023";
        String month = "1";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", year);
        params.add("month", month);

        Object[] list = {true, new SalaryStatement()};

        SalaryStatementDto.PreContent preContent = SalaryStatementDto.PreContent.builder()
                .member(PreDto.Member.builder()
                        .companyMemberId(1L)
                        .name("근로자 이름")
                        .bankName("은행 이름")
                        .accountNumber("계좌 번호")
                        .accountHolder("예금주").build())
                .status(List.of(PreDto.Status.builder()
                        .statusId(1L)
                        .startTime(LocalDateTime.now())
                        .finishTime(LocalDateTime.now())
                        .note("특이사항 종류").build()))
                .statement(PreDto.Statement.builder()
                        .year(2023)
                        .month(1)
                        .basePay(BigDecimal.valueOf(10000))
                        .overtimePay(BigDecimal.valueOf(10000))
                        .nightWorkAllowance(BigDecimal.valueOf(10000))
                        .holidayWorkAllowance(BigDecimal.valueOf(10000))
                        .unpaidLeave(BigDecimal.valueOf(10000))
                        .salary(BigDecimal.valueOf(10000))
                        .incomeTax(BigDecimal.valueOf(10000))
                        .nationalCoalition(BigDecimal.valueOf(10000))
                        .healthInsurance(BigDecimal.valueOf(10000))
                        .employmentInsurance(BigDecimal.valueOf(10000))
                        .deduction(BigDecimal.valueOf(10000))
                        .totalSalary(BigDecimal.valueOf(10000)).build())
                .exist(true)
                .salaryStatementId(1L).build();

        given(salaryStatementService.getPreContent(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementService.check(Mockito.any(SalaryStatement.class))).willReturn(list);
        given(salaryStatementMapper.preContent(Mockito.any(SalaryStatement.class), Mockito.anyBoolean(), Mockito.any(SalaryStatement.class))).willReturn(preContent);

        mockMvc.perform(get("/manager/{company-id}/members/{companymember-id}/paystub", 1L, 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-preSalaryStatement",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("company-id").description("회사 식별번호"),
                                parameterWithName("companymember-id").description("사원 번호")
                        ),
                        requestParameters(
                                List.of(
                                        parameterWithName("year").description("해당 년도"),
                                        parameterWithName("month").description("해당 월")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("member").type(JsonFieldType.OBJECT).description("직원 정보"),
                                        fieldWithPath("member.companyMemberId").type(JsonFieldType.NUMBER).description("사원 번호"),
                                        fieldWithPath("member.name").type(JsonFieldType.STRING).description("직원 이름"),
                                        fieldWithPath("member.bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("member.accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("member.accountHolder").type(JsonFieldType.STRING).description("예금주"),
                                        fieldWithPath("status[]").type(JsonFieldType.ARRAY).description("특이사항 배열"),
                                        fieldWithPath("status[].statusId").type(JsonFieldType.NUMBER).description("특이사항 식별 번호"),
                                        fieldWithPath("status[].startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                        fieldWithPath("status[].finishTime").type(JsonFieldType.STRING).description("특이사항 종료 시간"),
                                        fieldWithPath("status[].note").type(JsonFieldType.STRING).description("특이사항 내용"),
                                        fieldWithPath("statement").type(JsonFieldType.OBJECT).description("급여 명세서 미리보기"),
                                        fieldWithPath("statement.year").type(JsonFieldType.NUMBER).description("해당 년도"),
                                        fieldWithPath("statement.month").type(JsonFieldType.NUMBER).description("해당 월"),
                                        fieldWithPath("statement.basePay").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("statement.overtimePay").type(JsonFieldType.NUMBER).description("연장 근로 수당"),
                                        fieldWithPath("statement.nightWorkAllowance").type(JsonFieldType.NUMBER).description("야간 근로 수당"),
                                        fieldWithPath("statement.holidayWorkAllowance").type(JsonFieldType.NUMBER).description("휴일 근로 수당"),
                                        fieldWithPath("statement.unpaidLeave").type(JsonFieldType.NUMBER).description("무급 휴가로 인한 기본급 차감"),
                                        fieldWithPath("statement.salary").type(JsonFieldType.NUMBER).description("지급액 계"),
                                        fieldWithPath("statement.incomeTax").type(JsonFieldType.NUMBER).description("소득세"),
                                        fieldWithPath("statement.nationalCoalition").type(JsonFieldType.NUMBER).description("국민 연금"),
                                        fieldWithPath("statement.healthInsurance").type(JsonFieldType.NUMBER).description("건강 보험"),
                                        fieldWithPath("statement.employmentInsurance").type(JsonFieldType.NUMBER).description("고용 보험"),
                                        fieldWithPath("statement.deduction").type(JsonFieldType.NUMBER).description("총 공제액"),
                                        fieldWithPath("statement.totalSalary").type(JsonFieldType.NUMBER).description("실 수령액"),
                                        fieldWithPath("exist").type(JsonFieldType.BOOLEAN).description("해당 기간 급여 명세서 발급 여부"),
                                        fieldWithPath("salaryStatementId").type(JsonFieldType.NUMBER).description("급여 명세서 식별 번호 (기존 발급분이 존재한다면)")
                                )
                        )));
    }

    @Test
    @DisplayName("SalaryStatement Post Test")
    public void postSalaryStatementTest() throws Exception {
        String year = "2023";
        String month = "1";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", year);
        params.add("month", month);

        doNothing().when(salaryStatementService).createSalaryStatement(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong());

        mockMvc.perform(post("/manager/{company-id}/members/{companymember-id}/paystub", 1L, 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-SalaryStatement",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("company-id").description("회사 식별번호"),
                                parameterWithName("companymember-id").description("사원 번호")
                        ),
                        requestParameters(
                                List.of(
                                        parameterWithName("year").description("해당 년도"),
                                        parameterWithName("month").description("해당 월")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("SalaryStatement Get Test")
    public void getSalaryStatementTest() throws Exception {
        given(salaryStatementService.findSalaryStatement(Mockito.anyLong(), Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementMapper.salaryStatementToResponse(Mockito.any(SalaryStatement.class))).willReturn(StubData.MockSalaryStatement.getResponseBody());

        mockMvc.perform(get("/paystub/{salarystatement-id}", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-SalaryStatement",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                getRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("급여명세서 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("year").type(JsonFieldType.NUMBER).description("급여명세서 발행 년도"),
                                        fieldWithPath("month").type(JsonFieldType.NUMBER).description("급여명세서 발행 월"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("직원 이름"),
                                        fieldWithPath("team").type(JsonFieldType.STRING).description("직원이 속한 부서명"),
                                        fieldWithPath("grade").type(JsonFieldType.STRING).description("직원 직급명"),
                                        fieldWithPath("hourlyWage").type(JsonFieldType.NUMBER).description("통상시급"),
                                        fieldWithPath("basePay").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("overtimePay").type(JsonFieldType.NUMBER).description("연장근로수당"),
                                        fieldWithPath("overtimePayBasis").type(JsonFieldType.NUMBER).description("연장근로수당 책정 근거"),
                                        fieldWithPath("nightWorkAllowance").type(JsonFieldType.NUMBER).description("야간근로수당"),
                                        fieldWithPath("nightWorkAllowanceBasis").type(JsonFieldType.NUMBER).description("야간근로수당 책정 근거"),
                                        fieldWithPath("holidayWorkAllowance").type(JsonFieldType.NUMBER).description("휴일근로수당"),
                                        fieldWithPath("holidayWorkAllowanceBasis").type(JsonFieldType.NUMBER).description("휴일근로수당 책정 근거"),
                                        fieldWithPath("unpaidLeave").type(JsonFieldType.NUMBER).description("무급휴가로 인한 기본급 차감"),
                                        fieldWithPath("salary").type(JsonFieldType.NUMBER).description("지급액 계"),
                                        fieldWithPath("incomeTax").type(JsonFieldType.NUMBER).description("소득세"),
                                        fieldWithPath("nationalCoalition").type(JsonFieldType.NUMBER).description("국민연금"),
                                        fieldWithPath("employmentInsurance").type(JsonFieldType.NUMBER).description("고용보험"),
                                        fieldWithPath("healthInsurance").type(JsonFieldType.NUMBER).description("건강보험"),
                                        fieldWithPath("totalSalary").type(JsonFieldType.NUMBER).description("실수령액"),
                                        fieldWithPath("bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("paymentStatus").type(JsonFieldType.BOOLEAN).description("지급 여부")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("SalaryStatement Delete Test")
    public void deleteSalaryStatementTest() throws Exception {
        doNothing().when(salaryStatementService).deleteSalaryStatement(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(delete("/paystub/{salarystatement-id}", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(
                        document(
                                "delete-SalaryStatement",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getRequestPathParameterDescriptor()
                                )
                        )
                );
    }

    @Test
    @DisplayName("SalaryStatement Download Test")
    public void downloadSalaryStatementTest() throws Exception {
        given(salaryStatementService.findSalaryStatement(Mockito.anyLong(), Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementService.makePdf(Mockito.any(SalaryStatement.class))).willReturn(new ByteArrayOutputStream());

        ByteArrayOutputStream content = new ByteArrayOutputStream();

        MvcResult result = mockMvc.perform(get("/paystub/{salarystatement-id}/file", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andReturn();

        content.write(result.getResponse().getContentAsByteArray());

        document(
                "download-SalaryStatement",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                        parameterWithName("salarystatement-id").description("급여명세서 식별 번호")
                ),
                responseHeaders(
                        headerWithName("Content-Type").description("파일 형식"),
                        headerWithName("Content-Disposition").description("attachment; filename={year}_{month}.pdf/jpeg/png")
                ),
                responseFields(
                        fieldWithPath("binaryContent").description("파일 바이너리 데이터")
                )
        );
    }

    @Test
    @DisplayName("Email Send Test")
    public void sendEmailTest() throws Exception {
        doNothing().when(salaryStatementService).sendEmail(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(post("/paystub/{salarystatement-id}/email", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "send-email",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("salarystatement-id").description("급여명세서 식별 번호")
                                )
                        )
                );
    }
}
