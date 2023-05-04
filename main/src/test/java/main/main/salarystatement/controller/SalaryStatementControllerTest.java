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
import org.springframework.test.web.servlet.MockMvc;

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
                .andDo(print());
    }

    @Test
    @DisplayName("SalaryStatement Get Test")
    public void getSalaryStatementTest() throws Exception {
        given(salaryStatementService.findSalaryStatement(Mockito.anyLong())).willReturn(new SalaryStatement());
        given(salaryStatementMapper.salaryStatementToResponse(Mockito.any(SalaryStatement.class))).willReturn(StubData.MockSalaryStatement.getResponseBody());

        mockMvc.perform(getRequestBuilder(SALARYSTATEMENT_RESOURCE_URI, 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("SalaryStatement Delete Test")
    public void deleteSalaryStatementTest() throws Exception {
        doNothing().when(salaryStatementService).deleteSalaryStatement(Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(SALARYSTATEMENT_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
