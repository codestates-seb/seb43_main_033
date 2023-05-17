package main.main.laborcontract.controller;

import main.main.auth.jwt.JwtTokenizer;
import main.main.company.entity.Company;
import main.main.helper.LaborContractHelper;
import main.main.helper.StubData;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LaborContractController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LaborContractControllerTest implements LaborContractHelper {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private LaborContractService laborContractService;
    @MockBean
    private LaborContractMapper laborContractMapper;
    private String accessToken;
    @BeforeAll
    public void init() {
        System.out.println("# BeforeAll");
        accessToken = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }

    @Test
    @DisplayName("LaborContract Post Test")
    public void postLaborContractTest() throws Exception {
        LaborContractDto.Post post = (LaborContractDto.Post) StubData.MockLaborContract.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);
        MockMultipartFile jsonFile = new MockMultipartFile("requestPart", "", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile pdfFile = new MockMultipartFile("file", "test.png", "image/png", " ".getBytes());

        given(laborContractMapper.postToLaborContract(Mockito.any(LaborContractDto.Post.class))).willReturn(new LaborContract());
        doNothing().when(laborContractService).creatLaborContract(Mockito.any(LaborContract.class), Mockito.any(MultipartFile.class), Mockito.anyLong());

        mockMvc.perform(multipart("/manager/{company-id}/members/{companymember-id}/laborcontracts", 1L, 1L, accessToken)
                        .file(jsonFile)
                        .file(pdfFile)
                        .contentType("multipart/form-data")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParts(
                                partWithName("requestPart").description("내용"),
                                partWithName("file").description("PDF / PNG / JPEG 파일")
                        ),
                        requestPartFields(
                                "requestPart",
                                    fieldWithPath("companyMemberId").type(JsonFieldType.NUMBER).description("사원 번호"),
                                    fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                    fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                    fieldWithPath("startOfContract").type(JsonFieldType.STRING).description("계약 시작일"),
                                    fieldWithPath("endOfContract").type(JsonFieldType.STRING).description("계약 만료일"),
                                    fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                    fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                    fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                        ))
                );
    }

    @Test
    @DisplayName("LaborContract Patch Test")
    public void patchLaborContractTest() throws Exception {
        LaborContractDto.Patch patch = (LaborContractDto.Patch) StubData.MockLaborContract.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);
        MockMultipartFile jsonFile = new MockMultipartFile("requestPart", "", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile pdfFile = new MockMultipartFile("file", "test.png", "image/png", " ".getBytes());

        LaborContract laborContract = new LaborContract();
        laborContract.setCompany(new Company());

        given(laborContractMapper.patchToLaborContract(Mockito.any(LaborContractDto.Patch.class))).willReturn(laborContract);
        doNothing().when(laborContractService).updateLaborContract(Mockito.anyLong(), Mockito.any(LaborContract.class), Mockito.any(MultipartFile.class), Mockito.anyLong());

        mockMvc.perform(multipartPatchBuilder("/manager/{company-id}/laborcontracts/{laborcontract-id}", 1L, 1L, accessToken)
                .file(jsonFile)
                .file(pdfFile)
                .contentType("multipart/form-data")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
//                        pathParameters(
//                                parameterWithName("company-id").description("회사 식별 번호"),
//                                parameterWithName("laborcontract-id").description("근로계약서 식별 번호")
//                        ),
                        requestParts(
                                partWithName("requestPart").description("내용"),
                                partWithName("file").description("PDF / PNG / JPEG 파일")
                        ),
                        requestPartFields(
                                "requestPart",
                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )
                );
    }

    @Test
    @DisplayName("LaborContract Get Test")
    public void getLaborContractTest() throws Exception {
        given(laborContractService.findLaborContract(Mockito.anyLong(), Mockito.anyLong())).willReturn(new LaborContract());
        given(laborContractMapper.laborContractToResponse(Mockito.any(LaborContract.class))).willReturn(StubData.MockLaborContract.getResponseBody());

        mockMvc.perform(get("/manager/{company-id}/laborcontracts/{laborcontract-id}", 1L, 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("company-id").description("회사 식별 번호"),
                                parameterWithName("laborcontract-id").description("근로계약서 식별 번호")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("laborContactId").type(JsonFieldType.NUMBER).description("근로계약서 식별 번호"),
                                        fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("accountHolder").type(JsonFieldType.STRING).description("예금주"),
                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("LaborContract Get Test In MyPage")
    public void getLaborContractList() throws Exception {
        List<LaborContract> list = List.of(
                new LaborContract()
        );

        given(laborContractService.findLaborContract(Mockito.anyLong())).willReturn(list);
        given(laborContractMapper.laborContractsToResponses(Mockito.anyList())).willReturn(StubData.MockLaborContract.getMultiResponseBody());

        mockMvc.perform(get("/worker/mycontract", accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andDo(document("get-LaborContract-in-mypage",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("근로계약서 리스트"),
                                        fieldWithPath("[].laborContactId").type(JsonFieldType.NUMBER).description("근로계약서 식별 번호"),
                                        fieldWithPath("[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("[].bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("[].accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("[].accountHolder").type(JsonFieldType.STRING).description("예금주"),
                                        fieldWithPath("[].basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("[].startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("[].finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("[].information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )));
    }

    @Test
    @DisplayName("LaborContract Image Get Test")
    public void getLaborContractImageTest() throws Exception {
        given(laborContractService.getImage(Mockito.anyLong(), Mockito.anyLong())).willReturn(StubData.MockLaborContract.getImage());

        mockMvc.perform(getImageRequestBuilder(LABORCONTRACT_IMAGE_URI, 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-LaborContract_IMAGE",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                getRequestPathParameterDescriptor()
                        )
                ));

    }

    @Test
    @DisplayName("LaborContract Delete Test")
    public void deleteLaborContractTest() throws Exception {
        doNothing().when(laborContractService).deleteLaborContract(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(LABORCONTRACT_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-LaborContract",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getRequestPathParameterDescriptor()
                                )
                        )
                );
    }

    // multipart 가 post 로 고정되어 있어 Patch 로 바꿔줌
    private MockMultipartHttpServletRequestBuilder multipartPatchBuilder(final String url, long companyId, long laborcontractId, String accessToken) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url, companyId, laborcontractId, accessToken);
        builder.with(request1 -> {
            request1.setMethod(HttpMethod.PATCH.name());
            request1.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, getPathVariableMap(companyId, laborcontractId, accessToken));
            return request1;
        });
        return builder;
    }

    private Map<String, String> getPathVariableMap(long companyId, long laborcontractId, String accessToken) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("companyId", String.valueOf(companyId));
        pathVariables.put("laborcontractId", String.valueOf(laborcontractId));
        pathVariables.put("accessToken", accessToken);
        return pathVariables;
    }
}
