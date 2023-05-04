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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
                .andDo(print());
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
                .andDo(print());
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
                .andDo(print());
    }

    @Test
    @DisplayName("StatusOfWork Delete Test")
    public void deleteStatusOfWorkTest() throws Exception {
        doNothing().when(statusOfWorkService).deleteStatusOfWork(Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(STATUSOFWORK_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
