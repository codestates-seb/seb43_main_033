package main.main.companymember.controller;

import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.dto.Status;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static main.main.helper.StubData.MockCompanyMember.getCompanyMemberResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
        given(companyMemberService.createCompanyMember(Mockito.any(CompanyMember.class), Mockito.anyLong())).willReturn(new CompanyMember());

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
        given(companyMemberService.updateCompanyMember(Mockito.any(CompanyMember.class), Mockito.anyLong())).willReturn(new CompanyMember());
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
                                        fieldWithPath("companyMemberId").type(JsonFieldType.NUMBER).description("회사 회원 식별 번호"),
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("회사 회원 이름"),
                                        fieldWithPath("grade").type(JsonFieldType.STRING).description("회사 회원 직급"),
                                        fieldWithPath("team").type(JsonFieldType.STRING).description("회사 회원 소속 부서"),
                                        fieldWithPath("roles").type(JsonFieldType.ARRAY).description("회사 회원 권한").optional(),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("회사 회원 상태"),
                                        fieldWithPath("remainVacation").type(JsonFieldType.NUMBER).description("잔여 휴가 일수")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("CompanyMembers Get Test")
    public void getCompanyMembersTest() throws  Exception {
        int page = 1;
        String status = "pending";
        Long companyId = 1L;

        CompanyMember companyMember1 = new CompanyMember();
        companyMember1.setCompanyMemberId(1L);
        companyMember1.setTeam("부서");
        companyMember1.setGrade("직급");
        companyMember1.setStatus(Status.PENDING);


        CompanyMember companyMember2 = new CompanyMember();
        companyMember2.setCompanyMemberId(2L);
        companyMember2.setTeam("부서");
        companyMember2.setGrade("직급");
        companyMember2.setStatus(Status.PENDING);

        List<CompanyMember> companyMembers = Arrays.asList(companyMember1, companyMember2);
        Page<CompanyMember> companyMemberPage = new PageImpl<>(companyMembers);

        given(companyMemberService.findCompanyMembersByCompanyId(
                Mockito.anyInt(), Mockito.anyString(), Mockito.anyLong()))
                .willReturn(companyMemberPage);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/companymembers")
                        .param("page", String.valueOf(page))
                        .param("status", status)
                        .param("companyId", String.valueOf(companyId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("get-companyMembers",
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("회사 회원 목록").optional(),
                                fieldWithPath("data[].companyMemberId").type(JsonFieldType.NUMBER).description("회사 회원 식별 번호"),
                                fieldWithPath("data[].status").type(JsonFieldType.STRING).description("회사 회원 승인 상태"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 회원 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )));
    }



    @Test
    @DisplayName("CompanyMember Delete Test")
    public void deleteCompanyMemberTest() throws Exception {
        doNothing().when(companyMemberService).deleteCompanyMember(anyLong(), Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(COMPANYMEMBER_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(document("delete-companymember",
                        pathParameters(
                                parameterWithName("companymember-id").description("회사 사원 식별 번호")
                        )
                ));
    }


    @Test
    @DisplayName("CompanyMember Pending Test")
    public void pendingCompanyMemberTest() throws Exception {
        long companyMemberId = 1L;
        String status = "pending";

        mockMvc.perform(RestDocumentationRequestBuilders.post(
                                "/companymembers/pending/{companymember-id}/{status}", companyMemberId, status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("pendding-companyMember",
                        pathParameters(
                                parameterWithName("companymember-id").description("회사 사원 식별 번호"),
                                parameterWithName("status").description("승인 여부")
                        )));
    }

//    @Test
//    @DisplayName("updateMember Role Test")
//    public void updateMemberRoleTest() throws Exception {
//
//        long companyMemberId = 1L;
//        CompanyMemberDto.Roles roles = new CompanyMemberDto.Roles();
//        roles.setRoles(Arrays.asList("MANAGER"));
//
//        CompanyMember updatedCompanyMember = new CompanyMember();
//
//        given(companyMemberService.updateCompanyMemberRole(
//                Mockito.eq(companyMemberId), Mockito.any(CompanyMemberDto.Roles.class)))
//                .willReturn(updatedCompanyMember);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.patch(
//                                "/companymembers/role/{companymember-id}", companyMemberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toJsonContent(roles)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(document("update-memberRole",
//                        pathParameters(
//                                parameterWithName("companymember-id").description("회사 사원 식별 번호")
//                        ),
//                        requestFields(
//                                fieldWithPath("roles").description("업데이트 전 역할")
//                        )
//                ));
//    }

}
