package main.main.companymember.controller;

import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.mapper.CompanyMemberMapper;
import main.main.companymember.service.CompanyMemberService;
import main.main.helper.CompanyMemberHelper;
import main.main.helper.StubData;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static main.main.helper.StubData.MockCompanyMember.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyMemberController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyMemberControllerTest implements CompanyMemberHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyMemberService companyMemberService;

    @MockBean
    private CompanyMemberMapper companyMemberMapper;
    @Test
    @DisplayName("CompanyMember Create Test")
    public void createCompanyMemberTest() throws Exception {
        CompanyMemberDto.Post post = (CompanyMemberDto.Post) StubData.MockCompanyMember.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(companyMemberMapper.companyMemberPostToCompanyMember(Mockito.any(CompanyMemberDto.Post.class))).willReturn(new CompanyMember());
        given(companyMemberService.createCompanyMember(Mockito.any(CompanyMember.class))).willReturn(new CompanyMember());

        ResultActions action =
                mockMvc.perform(postRequestBuilder(COMPANYMEMBER_DEFAULT_URL, 1L, content));

        action
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-companymember",
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("grade").type(JsonFieldType.STRING).description("회원 직급"),
                                        fieldWithPath("team").type(JsonFieldType.STRING).description("회원 소속 부서")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("CompanyMember Update Test")
    public void patchCompanyMemberTest() throws Exception {
        CompanyMemberDto.Patch patch = (CompanyMemberDto.Patch) StubData.MockCompanyMember.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);

        given(companyMemberMapper.companyMemberPacthToCompanyMember(Mockito.any(CompanyMemberDto.Patch.class))).willReturn(new CompanyMember());
        given(companyMemberService.updateCompanyMember(Mockito.any(CompanyMember.class))).willReturn(new CompanyMember());
        given(companyMemberMapper.companyMemberToCompanyMemberResponse(Mockito.any(CompanyMember.class))).willReturn(getCompanyMemberResponse());

        ResultActions actions =
                mockMvc.perform(patchRequestBuilder(COMPANYMEMBER_RESOURCE_URI, 1L, content));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-companymember",
                        pathParameters(
                                getCompanyMemberRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("companyMemberId").type(JsonFieldType.NUMBER).description("회사 사원 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("grade").type(JsonFieldType.STRING).description("회원 직급"),
                                        fieldWithPath("team").type(JsonFieldType.STRING).description("회원 소속 부서")
                                )
                        )
                ));
    }



    @Test
    @DisplayName("CompanyMember Get Test")
    public void getCompanyMemberTest() throws Exception {

        given(companyMemberService.findCompanyMember(anyLong())).willReturn(new CompanyMember());
        given(companyMemberMapper.companyMemberToCompanyMemberResponse(Mockito.any(CompanyMember.class))).willReturn(getCompanyMemberResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(COMPANYMEMBER_RESOURCE_URI, 1L));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-companymember",
                        pathParameters(
                                getCompanyMemberRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("companyMemberId").type(JsonFieldType.NUMBER).description("회사 사원 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("grade").type(JsonFieldType.STRING).description("사원 직급"),
                                        fieldWithPath("team").type(JsonFieldType.STRING).description("사원 소속 부서")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("CompanyMembers Get Test")
    public void getCompanyMembersTest() throws  Exception {
        String page = "1";
        String size = "5";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page);
        params.add("size", size);

        given(companyMemberService.findCompanyMembers(Mockito.anyInt(), Mockito.anyInt())).willReturn(getCompanyMembersByPage());
        given(companyMemberMapper.companyMembersToCompanyMembersResponse(Mockito.anyList())).willReturn(getCompanyMembersToCompanyMembersResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(COMPANYMEMBER_DEFAULT_URL, params));

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-members",
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지"),
                                        parameterWithName("size").description("한 페이지내 항목 수")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[].companyMemberId").type(JsonFieldType.NUMBER).description("회사 사원 식별 번호"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("data[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("data[].grade").type(JsonFieldType.STRING).description("사원 직급"),
                                        fieldWithPath("data[].team").type(JsonFieldType.STRING).description("사원 소속 부서"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 회사 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")

                                )
                        )
                ));
    }



    @Test
    @DisplayName("CompanyMember Delete Test")
    public void deleteCompanyMemberTest() throws Exception {
        doNothing().when(companyMemberService).deleteCompanyMember(anyLong());

        mockMvc.perform(deleteRequestBuilder(COMPANYMEMBER_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(document("delete-companymember",
                        pathParameters(
                                parameterWithName("companymember-id").description("회사 사원 식별 번호")
                        )
                ));
    }

}
