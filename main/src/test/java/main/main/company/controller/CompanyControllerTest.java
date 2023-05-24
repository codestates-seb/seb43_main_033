package main.main.company.controller;

import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.helper.CompanyHelper;
import main.main.helper.StubData;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.main.helper.StubData.MockCompany.getCompaniesByPage;
import static main.main.helper.StubData.MockCompany.getCompaniesToCompaniesResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyControllerTest implements CompanyHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private CompanyMember companyMember;

    @MockBean
    private MemberService memberService;

    @MockBean
    private CompanyMapper companyMapper;

    @Test
    @DisplayName("Company Create Test")
    public void createCompanyTest() throws Exception {
        CompanyDto.Post post = (CompanyDto.Post) StubData.MockCompany.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(companyMapper.companyPostToCompany(Mockito.any(CompanyDto.Post.class))).willReturn(new Company());
        given(companyService.createCompany(Mockito.any(Company.class), Mockito.anyLong())).willReturn(new Company());

        ResultActions actions =
                mockMvc.perform(postRequestBuilder(COMPANY_DEFAULT_URL, 1L, content));

        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-company",
                        requestFields(
                                List.of(
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companySize").type(JsonFieldType.STRING).description("회사 규모"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("회사 주소"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("회사 정보")
                                )
                        )
                ));


    }


    @Test
    @DisplayName("Company Update Test")
    public void patchCompanyTest() throws Exception {
        CompanyDto.Patch patch = (CompanyDto.Patch) StubData.MockCompany.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);
        Company company = new Company();
        long authenticationMemberId = 1L;
        String businessNumber = "1234567890";
        List<CompanyMember> companyMemberList = new ArrayList<>();
        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompany(new Company());
        companyMember.getCompany().setCompanyId(1L);
        companyMember.getCompany().setCompanyName("Test Company");
        companyMember.getCompany().setBusinessNumber(businessNumber);
        companyMemberList.add(companyMember);
        BigDecimal[] salaryOfCompany = {BigDecimal.valueOf(100000), BigDecimal.valueOf(200000)};

        given(companyMapper.companyPatchToCompany(Mockito.any(CompanyDto.Patch.class))).willReturn(company);
        given(companyService.updateCompany(company, businessNumber, authenticationMemberId)).willReturn(company);
        given(companyMapper.companyToCompanyResponse(company, salaryOfCompany, companyMemberList)).willReturn(StubData.MockCompany.getCompanyResponse());


        ResultActions actions =
                mockMvc.perform(patchRequestBuilder(COMPANY_RESOURCE_URI, 1L, content));

        actions
                .andExpect(status().isOk())


                .andDo(print())
                .andDo(document("patch-company",
                        pathParameters(
                                getCompanyRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companySize").type(JsonFieldType.STRING).description("회사 규모"),
                                        fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 등록 번호"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("회사 주소"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("회사 정보")
                                )
                        )
                ));
    }


    @Test
    @DisplayName("Company Get Test")
    public void getCompanyTest() throws Exception {

        given(companyService.findCompany(Mockito.anyLong())).willReturn(new Company());
        given(companyMapper.companyToCompanyResponse(Mockito.any(Company.class), Mockito.any(), Mockito.anyList())).willReturn(StubData.MockCompany.getCompanyResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(COMPANY_RESOURCE_URI, 1L));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-company",
                        pathParameters(
                                getCompanyRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companySize").type(JsonFieldType.STRING).description("회사 규모"),
                                        fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 등록 번호"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("회사 주소"),
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("회사 정보"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호").optional(),
                                        fieldWithPath("theSalaryOfTheCompanyThisMonth").type(JsonFieldType.NUMBER).description("이번 달 총 급여"),
                                        fieldWithPath("theSalaryOfTheCompanyLastMonth").type(JsonFieldType.NUMBER).description("지난 달 총 급여"),
                                        fieldWithPath("companyMembers[].companyMemberId").type(JsonFieldType.NUMBER).description("사원 식별 번호").optional(),
                                        fieldWithPath("companyMembers[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호").optional(),
                                        fieldWithPath("companyMembers[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호").optional(),
                                        fieldWithPath("companyMembers[].name").type(JsonFieldType.STRING).description("사원명").optional(),
                                        fieldWithPath("companyMembers[].grade").type(JsonFieldType.STRING).description("사원 직급").optional(),
                                        fieldWithPath("companyMembers[].companyMemberId").type(JsonFieldType.NUMBER).description("사원 식별 번호").optional(),
                                        fieldWithPath("companyMembers[].team").type(JsonFieldType.STRING).description("사원 소속 부서").optional(),
                                        fieldWithPath("companyMembers[].roles").type(JsonFieldType.STRING).description("사원 역할 상태").optional(),
                                        fieldWithPath("companyMembers[].status").type(JsonFieldType.STRING).description("현재 소속 상태").optional()

                                )
                        )
                ));


    }


    @Test
    @DisplayName("Companies Get Test")
    public void getCompaniesTest() throws Exception {
        String page = "1";
        String size = "5";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page);
        params.add("size", size);

        given(companyService.findCompanies(Mockito.anyInt(), Mockito.anyInt())).willReturn(getCompaniesByPage());
        given(companyMapper.companiesToCompaniesResponse(Mockito.anyList())).willReturn(getCompaniesToCompaniesResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(COMPANY_DEFAULT_URL, params));

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andDo(document("get-companies",
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지"),
                                        parameterWithName("size").description("한 페이지내 항목 수")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("data[].companySize").type(JsonFieldType.STRING).description("회사 규모"),
                                        fieldWithPath("data[].businessNumber").type(JsonFieldType.STRING).description("사업자 등록 번호"),
                                        fieldWithPath("data[].address").type(JsonFieldType.STRING).description("회사 주소"),
                                        fieldWithPath("data[].information").type(JsonFieldType.STRING).description("회사 정보"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호").optional(),
                                        fieldWithPath("data[].companyMembers[].companyMemberId").type(JsonFieldType.NUMBER).description("사원 식별 번호").optional(),
                                        fieldWithPath("data[].companyMembers[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호").optional(),
                                        fieldWithPath("data[].companyMembers[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호").optional(),
                                        fieldWithPath("data[].companyMembers[].name").type(JsonFieldType.STRING).description("사원명").optional().optional(),
                                        fieldWithPath("data[].companyMembers[].grade").type(JsonFieldType.STRING).description("사원 직급").optional(),
                                        fieldWithPath("data[].companyMembers[].companyMemberId").type(JsonFieldType.NUMBER).description("사원 식별 번호").optional(),
                                        fieldWithPath("data[].companyMembers[].team").type(JsonFieldType.STRING).description("사원 소속 부서").optional(),
                                        fieldWithPath("data[].companyMembers[].roles").type(JsonFieldType.STRING).description("사원 역할 상태").optional(),
                                        fieldWithPath("data[].companyMembers[].status").type(JsonFieldType.STRING).description("현재 소속 상태").optional(),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 회사 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                ));

    }


    @Test
    @DisplayName("Company Delete Test")
    public void deleteCompanyTest() throws Exception {
        doNothing().when(companyService).deleteCompany(anyLong(), Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(COMPANY_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(document("delete-company",
                        pathParameters(
                                parameterWithName("company-id").description("회사 식별 번호")
                        )
                ));
    }


    @Test
    @DisplayName("Get BusinessNumbers by Role Test")
    public void getBusinessNumberByMemberTest() throws Exception {
        long memberId = 1L;
        long authenticationMemberId = 1L;

        Member member = new Member();
        member.setMemberId(memberId);

        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompany(new Company());
        companyMember.getCompany().setBusinessNumber("1234567890");
        member.setCompanyMembers(List.of(companyMember));

        List<Map<String, Object>> businessList = new ArrayList<>();
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("companyId", companyMember.getCompany().getCompanyId());
        businessData.put("companyName", companyMember.getCompany().getCompanyName());
        businessData.put("businessNumber", companyMember.getCompany().getBusinessNumber());
        businessList.add(businessData);

        given(memberService.findMember(memberId)).willReturn(member);
        given(companyService.findBusinessNumbersByRole(memberId, authenticationMemberId)).willReturn(businessList);

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(BUSINESS_RESOURCE_URL, 1L));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-businessNumberByRole",

                        pathParameters(
                                getMemberRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                fieldWithPath("[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호").optional(),
                                fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사 이름").optional(),
                                fieldWithPath("[].businessNumber").type(JsonFieldType.STRING).description("사업자 등록 번호").optional()
                        )
                ));
    }
}