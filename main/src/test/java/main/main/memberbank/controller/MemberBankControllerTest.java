package main.main.memberbank.controller;

import main.main.helper.MemberBankHelper;
import main.main.helper.StubData;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import main.main.memberbank.mapper.MemberBankMapper;
import main.main.memberbank.service.MemberBankService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberBankController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberBankControllerTest implements MemberBankHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberBankService memberBankService;

    @MockBean
    private MemberBankMapper memberBankMapper;

    @Test
    @DisplayName("MemberBank Create Test")
    public void createMemberBankTest() throws Exception {
        MemberBankDto.Post post = (MemberBankDto.Post) StubData.MockMemberBank.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(memberBankMapper.memberBankPostToMemberBank(Mockito.any(MemberBankDto.Post.class))).willReturn(new MemberBank());
        given(memberBankService.createMemberBank(Mockito.any(MemberBank.class), Mockito.anyLong())).willReturn(new MemberBank());

        ResultActions actions =
                mockMvc.perform(postRequestBuilder(MEMBERBANK_DEFAULT_URL, 1L, content));

        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-memberbank",
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호"),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("mainAccount").type(JsonFieldType.BOOLEAN).description("주 사용 계좌 여부")
                                )
                        )
                ));
    }


    @Test
    @DisplayName("MemberBank Update Test")
    public void patchMemberBankTest() throws Exception {
        MemberBankDto.Patch patch = (MemberBankDto.Patch) StubData.MockMemberBank.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);

        given(memberBankMapper.memberBankPathToMemberBank(Mockito.any(MemberBankDto.Patch.class))).willReturn(new MemberBank());
        given(memberBankService.updateMemberBank(Mockito.any(MemberBank.class), Mockito.anyLong())).willReturn(new MemberBank());
        given(memberBankMapper.memberBankToMemberBankResponse(Mockito.any(MemberBank.class))).willReturn(StubData.MockMemberBank.getMemberBankResponse());

        ResultActions actions =
                mockMvc.perform(patchRequestBuilder(MEMBERBANK_RESOURCE_URI, 1L, content));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-memberbank",
                        pathParameters(
                                getMemberBankRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberBankId").type(JsonFieldType.NUMBER).description("계좌 정보 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호"),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("mainAccount").type(JsonFieldType.BOOLEAN).description("주 사용 계좌 여부")

                                )
                        )
                ));

    }

    @Test
    @DisplayName("MemberBank Get Test")
    public void getMemberBankTest() throws  Exception {

        given(memberBankService.findMemberBank(Mockito.anyLong())).willReturn(new MemberBank());
        given(memberBankMapper.memberBankToMemberBankResponse(Mockito.any(MemberBank.class))).willReturn(StubData.MockMemberBank.getMemberBankResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(MEMBERBANK_RESOURCE_URI, 1L));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-memberbank",
                        pathParameters(
                                getMemberBankRequestPathParameterDescriptor()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberBankId").type(JsonFieldType.NUMBER).description("계좌 정보 식별 번호"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호"),
                                        fieldWithPath("bankName").type(JsonFieldType.STRING).description("회원 계좌 은행명"),
                                        fieldWithPath("bankCode").type(JsonFieldType.STRING).description("회원 계좌 코드").optional(),
                                        fieldWithPath("accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("mainAccount").type(JsonFieldType.BOOLEAN).description("주 사용 계좌 여부")

                                )
                        )
                ));

    }

    @Test
    @DisplayName("MemberBanks Get Test")
    public void getMemberBanksTest() throws  Exception {
        String page = "1";
        String size = "5";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page);
        params.add("size", size);

        given(memberBankService.findMemberBanks(Mockito.anyInt(), Mockito.anyInt())).willReturn(StubData.MockMemberBank.getMemberBanksByPage());
        given(memberBankMapper.memberBanksToMemberBanksResponse(Mockito.anyList())).willReturn(StubData.MockMemberBank.getmemberBanksToMemberBanksResponse());

        ResultActions actions =
                mockMvc.perform(getRequestBuilder(MEMBERBANK_DEFAULT_URL, params));

        actions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-memberbanks",
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지"),
                                        parameterWithName("size").description("한 페이지 내 항목 수")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[].memberBankId").type(JsonFieldType.NUMBER).description("멤버 은행 식별 번호"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("data[].bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호"),
                                        fieldWithPath("data[].bankName").type(JsonFieldType.STRING).description("은행 이름"),
                                        fieldWithPath("data[].bankCode").type(JsonFieldType.STRING).description("회원 계좌 은행 코드").optional(),
                                        fieldWithPath("data[].accountNumber").type(JsonFieldType.STRING).description("계좌 번호"),
                                        fieldWithPath("data[].mainAccount").type(JsonFieldType.BOOLEAN).description("주 계좌 사용 여부"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 멤버 은행 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                ));
    }


    @Test
    @DisplayName("MemberBank Delete Test")
    public void deleteMemberBankTest() throws Exception {
        doNothing().when(memberBankService).deleteMemberBank(anyLong(), Mockito.anyLong());

        mockMvc.perform(deleteRequestBuilder(MEMBERBANK_RESOURCE_URI, 1L))
                .andExpect(status().isNoContent())
                .andDo(document("delete-memberbank",
                        pathParameters(
                                parameterWithName("memberbank-id").description("계좌 정보 식별 번호")
                        )
                ));
    }
}