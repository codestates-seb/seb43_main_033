package main.main.laborcontract.controller;

import main.main.helper.LaborContractHelper;
import main.main.helper.StubData;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
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

import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LaborContractController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
public class LaborContractControllerTest implements LaborContractHelper {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LaborContractService laborContractService;
    @MockBean
    private LaborContractMapper laborContractMapper;

    @Test
    @DisplayName("LaborContract Post Test")
    public void postLaborContractTest() throws Exception {
        LaborContractDto.Post post = (LaborContractDto.Post) StubData.MockLaborContract.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(laborContractMapper.postToLaborContract(Mockito.any(LaborContractDto.Post.class))).willReturn(new LaborContract());
        doNothing().when(laborContractService).creatLaborContract(Mockito.any(LaborContract.class));

        mockMvc.perform(postRequestBuilder(LABORCONTRACT_DEFAULT_URL, content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("startOfContract").type(JsonFieldType.STRING).description("계약 시작일"),
                                        fieldWithPath("endOfContract").type(JsonFieldType.STRING).description("계약 만료일"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )
                        ));
    }

    @Test
    @DisplayName("LaborContract Patch Test")
    public void patchLaborContractTest() throws Exception {
        LaborContractDto.Patch patch = (LaborContractDto.Patch) StubData.MockLaborContract.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);

        given(laborContractMapper.patchToLaborContract(Mockito.any(LaborContractDto.Patch.class))).willReturn(new LaborContract());
        doNothing().when(laborContractService).updateLaborContract(Mockito.anyLong(),Mockito.any(LaborContract.class));

        mockMvc.perform(patchRequestBuilder(LABORCONTRACT_RESOURCE_URI,1L, content))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                getRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("LaborContract Get Test")
    public void getLaborContractTest() throws Exception {
        given(laborContractService.findLaborContract(Mockito.anyLong())).willReturn(new LaborContract());
        given(laborContractMapper.laborContractToResponse(Mockito.any(LaborContract.class))).willReturn(StubData.MockLaborContract.getResponseBody());

        mockMvc.perform(getRequestBuilder(LABORCONTRACT_RESOURCE_URI, 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                getRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("LaborContract Delete Test")
    public void deleteLaborContractTest() throws Exception {
        doNothing().when(laborContractService).deleteLaborContract(Mockito.anyLong());

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
}
