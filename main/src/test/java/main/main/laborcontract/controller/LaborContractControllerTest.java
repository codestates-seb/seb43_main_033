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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
        MockMultipartFile jsonFile = new MockMultipartFile("requestPart", "", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile pdfFile = new MockMultipartFile("file", "test.png", "image/png", " ".getBytes());

        given(laborContractMapper.postToLaborContract(Mockito.any(LaborContractDto.Post.class))).willReturn(new LaborContract());
        doNothing().when(laborContractService).creatLaborContract(Mockito.any(LaborContract.class), Mockito.any(MultipartFile.class));

        mockMvc.perform(multipart(LABORCONTRACT_DEFAULT_URL)
                        .file(jsonFile)
                        .file(pdfFile)
                        .contentType("multipart/form-data")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParts(
                                partWithName("requestPart").description("내용"),
                                partWithName("file").description("PNG 파일")
                        ),
                        requestPartFields(
                                "requestPart",
                                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
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

        given(laborContractMapper.patchToLaborContract(Mockito.any(LaborContractDto.Patch.class))).willReturn(new LaborContract());
        doNothing().when(laborContractService).updateLaborContract(Mockito.anyLong(), Mockito.any(LaborContract.class), Mockito.any(MultipartFile.class));

        mockMvc.perform(multipartPutBuilder("/laborcontracts/{laborcontarct-id}", 1L)
                .file(jsonFile)
                .file(pdfFile)
                .contentType("multipart/form-data")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-LaborContract",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
//                        pathParameters(  // 요청을 강제로 바꿨더니 파라미터를 인식을 못함
//                                getRequestPathParameterDescriptor()
//                        ),
                        requestParts(
                                partWithName("requestPart").description("내용"),
                                partWithName("file").description("PNG 파일")
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
        given(laborContractService.findLaborContract(Mockito.anyLong())).willReturn(new LaborContract());
        given(laborContractMapper.laborContractToResponse(Mockito.any(LaborContract.class))).willReturn(StubData.MockLaborContract.getResponseBody());

        mockMvc.perform(getRequestBuilder(LABORCONTRACT_RESOURCE_URI, 1L));
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(document("get-LaborContract",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        pathParameters(
//                                getRequestPathParameterDescriptor()
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
//                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
//                                        fieldWithPath("basicSalary").type(JsonFieldType.NUMBER).description("기본급"),
//                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("업무 시작 시간"),
//                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("업무 마감 시간"),
//                                        fieldWithPath("information").type(JsonFieldType.STRING).description("근로계약서 정보")
//                                )
//                        )
//                ));

    }

    @Test
    @DisplayName("LaborContract Image Get Test")
    public void getLaborContractImageTest() throws Exception {
        given(laborContractService.getImage(Mockito.anyLong())).willReturn(StubData.MockLaborContract.getImage());

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

    // multipart 가 post 로 고정되어 있어 Patch 로 바꿔줌
    private MockMultipartHttpServletRequestBuilder multipartPutBuilder(final String url, long id) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url, id);
        builder.with(request1 -> {
            request1.setMethod(HttpMethod.PATCH.name());
            return request1;
        });
        return builder;
    }
}
