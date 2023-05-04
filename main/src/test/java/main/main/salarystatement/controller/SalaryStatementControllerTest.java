package main.main.salarystatement.controller;

import main.main.helper.SalaryStatementHelper;
import main.main.helper.StubData;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SalaryStatementController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
public class SalaryStatementControllerTest implements SalaryStatementHelper {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SalaryStatementService salaryStatementService;
    @MockBean
    private SalaryStatementMapper salaryStatementMapper;

    @Test
    @DisplayName("SalaryStatement Post Test")
    public void postSalaryStatementTest() throws Exception {
        SalaryStatementDto.Post post = (SalaryStatementDto.Post) StubData.MockSalaryStatement.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(salaryStatementMapper.postToSalaryStatement(Mockito.any(SalaryStatementDto.Post.class))).willReturn(new SalaryStatement());
        doNothing().when(salaryStatementService).createSalaryStatement(Mockito.any(SalaryStatement.class));

        mockMvc.perform(postRequestBuilder(SALARYSTATEMENT_DEFAULT_URL, content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-SalaryStatement",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("year").type(JsonFieldType.NUMBER).description("급여명세서 발행 년도"),
                                        fieldWithPath("month").type(JsonFieldType.NUMBER).description("급여명세서 발행 월")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("SalaryStatement Get Test")
    public void getSalaryStatementTest() throws Exception {
        given(salaryStatementService.findSalaryStatement(Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementMapper.salaryStatementToResponse(Mockito.any(SalaryStatement.class))).willReturn(StubData.MockSalaryStatement.getResponseBody());

        mockMvc.perform(getRequestBuilder(SALARYSTATEMENT_RESOURCE_URI, 1L))
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
                                        fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("year").type(JsonFieldType.NUMBER).description("급여명세서 발행 년도"),
                                        fieldWithPath("month").type(JsonFieldType.NUMBER).description("급여명세서 발행 월"),
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
                                        fieldWithPath("bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호"),
                                        fieldWithPath("bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("SalaryStatement Delete Test")
    public void deleteSalaryStatementTest() throws Exception {
        doNothing().when(salaryStatementService).deleteSalaryStatement(Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(SALARYSTATEMENT_RESOURCE_URI, 1L))
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
        given(salaryStatementService.findSalaryStatement(Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementService.makePdf(Mockito.any(SalaryStatement.class))).willReturn(new ByteArrayOutputStream());

        ByteArrayOutputStream content = new ByteArrayOutputStream();

        MvcResult result = mockMvc.perform(get("/salarystatements/{salarystatement-id}/payslip", 1L))
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
                        headerWithName("Content-Disposition").description("attachment; filename={year}_{month}.pdf")
                ),
                responseFields(
                        fieldWithPath("binaryContent").description("파일 바이너리 데이터")
                )
        );
    }

    @Test
    @DisplayName("Email Send Test")
    public void sendEmailTest() throws Exception {
        doNothing().when(salaryStatementService).sendEmail(Mockito.anyLong());

        mockMvc.perform(post("/salarystatements/{salarystatement-id}/payslip", 1L))
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
