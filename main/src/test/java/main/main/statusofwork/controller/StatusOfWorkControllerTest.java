package main.main.statusofwork.controller;

import main.main.helper.StatusOfWorkHelper;
import main.main.helper.StubData;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatusOfWorkController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
public class StatusOfWorkControllerTest implements StatusOfWorkHelper {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatusOfWorkService statusOfWorkService;
    @MockBean
    private StatusOfWorkMapper statusOfWorkMapper;

    @Test
    @DisplayName("StatusOfWork Post Test")
    public void postStatusOfWorkTest() throws Exception {
        StatusOfWorkDto.Post post = (StatusOfWorkDto.Post) StubData.MockStatusOfWork.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(statusOfWorkMapper.postToStatusOfWork(Mockito.any(StatusOfWorkDto.Post.class))).willReturn(new StatusOfWork());
        doNothing().when(statusOfWorkService).createStatusOfWork(Mockito.any(StatusOfWork.class));

        mockMvc.perform(postRequestBuilder(STATUSOFWORK_DEFAULT_URL, content))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-StatusOfWork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("특이사항 마감 시간"),
                                        fieldWithPath("note").type(JsonFieldType.STRING).description("특이사항 내용: 지각 / 조퇴 / 결근 / 연장근로 / 휴일근로 / 야간근로 / 유급휴가 / 무급휴가")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("StatusOfWork Patch Test")
    public void patchStatusOfWorkTest() throws Exception {
        StatusOfWorkDto.Patch patch = (StatusOfWorkDto.Patch) StubData.MockStatusOfWork.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);

        given(statusOfWorkMapper.patchToStatusOfWork(Mockito.any(StatusOfWorkDto.Patch.class))).willReturn(new StatusOfWork());
        doNothing().when(statusOfWorkService).updateStatusOfWork(Mockito.anyLong(), Mockito.any(StatusOfWork.class));

        mockMvc.perform(patchRequestBuilder(STATUSOFWORK_RESOURCE_URI, 1L, content))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-StatusOfWork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                getRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                        fieldWithPath("finishTime").type(JsonFieldType.STRING).description("특이사항 마감 시간"),
                                        fieldWithPath("note").type(JsonFieldType.STRING).description("특이사항 내용: 지각 / 조퇴 / 결근 / 연장근로 / 휴일근로 / 야간근로 / 유급휴가 / 무급휴가")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("StatusOfWork Get Test")
    public void getStatusOfWorkTest() throws Exception {
        String year = "2023";
        String month = "1";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", year);
        params.add("month", month);

        given(statusOfWorkService.findStatusOfWorks(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyLong())).willReturn(StubData.MockStatusOfWork.getStatusOfWorkList());
        given(statusOfWorkMapper.statusOfWorksToResponses(Mockito.anyList())).willReturn(StubData.MockStatusOfWork.getMultiResponseBody());

        mockMvc.perform(getRequestBuilderWithParams(STATUSOFWORK_RESOURCE_URI, 1L, params))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-StatusOfWork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(
                                        parameterWithName("year").description("해당 년도"),
                                        parameterWithName("month").description("해당 월")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("특이사항 식별 번호"),
                                        fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("[].startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                        fieldWithPath("[].finishTime").type(JsonFieldType.STRING).description("특이사항 마감 시간"),
                                        fieldWithPath("[].note").type(JsonFieldType.STRING).description("특이사항 내용: 지각 / 조퇴 / 결근 / 연장근로 / 휴일근로 / 야간근로 / 유급휴가 / 무급휴가")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("StatusOfWork Delete Test")
    public void deleteStatusOfWorkTest() throws Exception {
        doNothing().when(statusOfWorkService).deleteStatusOfWork(Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(STATUSOFWORK_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(
                        document(
                                "delete-StatusOfWork",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getRequestPathParameterDescriptor()
                                )
                        )
                );
    }
}
