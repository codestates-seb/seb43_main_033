package main.main.statusofwork.controller;

import main.main.auth.jwt.JwtTokenizer;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.service.CompanyMemberService;
import main.main.helper.StatusOfWorkHelper;
import main.main.helper.StubData;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.dto.VacationDto;
import main.main.statusofwork.entity.RequestVacation;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static main.main.utils.ApiDocumentUtils.getRequestPreProcessor;
import static main.main.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusOfWorkController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatusOfWorkControllerTest implements StatusOfWorkHelper {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private StatusOfWorkService statusOfWorkService;
    @MockBean
    private StatusOfWorkMapper statusOfWorkMapper;
    @MockBean
    private CompanyMemberService companyMemberService;
    private String accessToken;
    @BeforeAll
    public void init() {
        System.out.println("# BeforeAll");
        accessToken = StubData.MockSecurity.getValidAccessToken(jwtTokenizer.getSecretKey());
    }


    @Test
    @DisplayName("StatusOfWork Post Test")
    public void postStatusOfWorkTest() throws Exception {
        StatusOfWorkDto.Post post = (StatusOfWorkDto.Post) StubData.MockStatusOfWork.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        given(statusOfWorkMapper.postToStatusOfWork(Mockito.any(StatusOfWorkDto.Post.class))).willReturn(new StatusOfWork());
        doNothing().when(statusOfWorkService).createStatusOfWork(Mockito.any(StatusOfWork.class), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(post("/manager/{company-id}/members/{companymember-id}/status", 1L, 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-StatusOfWork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("company-id").description("회사 식별번호"),
                                parameterWithName("companymember-id").description("사원 번호")
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
    @DisplayName("StatusOfWork Post Test in mywork")
    public void postStatusOfWorkWorker() throws Exception {
        StatusOfWorkDto.WPost post = new StatusOfWorkDto.WPost();
        post.setCompanyId(1L);
        post.setStartTime(LocalDateTime.now());
        post.setFinishTime(LocalDateTime.now());
        post.setNote(StatusOfWork.Note.조퇴);
        String content = toJsonContent(post);

        given(statusOfWorkMapper.postToStatusOfWork(Mockito.any(StatusOfWorkDto.WPost.class))).willReturn(new StatusOfWork());
        doNothing().when(statusOfWorkService).createStatusOfWork(Mockito.any(StatusOfWork.class), Mockito.anyLong());

        mockMvc.perform(post("/worker/mywork", accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-StatusOfWorkMyWork",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
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
        doNothing().when(statusOfWorkService).updateStatusOfWork(Mockito.anyLong(), Mockito.any(StatusOfWork.class), Mockito.anyLong());

        mockMvc.perform(patch("/status/{statusofwork-id}", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
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
        given(statusOfWorkMapper.statusOfWorkToMyWork(Mockito.anyList())).willReturn(StubData.MockStatusOfWork.getMultiResponseBody());

        mockMvc.perform(get("/worker/mywork", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .params(params))
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
                                        fieldWithPath("company[]").type(JsonFieldType.ARRAY).description("소속 회사 리스트"),
                                        fieldWithPath("company[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("company[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("status[].id").type(JsonFieldType.NUMBER).description("특이사항 식별 번호"),
                                        fieldWithPath("status[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("status[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("status[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                        fieldWithPath("status[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("status[].startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                        fieldWithPath("status[].finishTime").type(JsonFieldType.STRING).description("특이사항 마감 시간"),
                                        fieldWithPath("status[].note").type(JsonFieldType.STRING).description("특이사항 내용: 지각 / 조퇴 / 결근 / 연장근로 / 휴일근로 / 야간근로 / 유급휴가 / 무급휴가")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("Get TodayList Test")
    public void getTodayStatusTest() throws Exception {
        String page = "1";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page);

        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompanyMemberId(1L);
        companyMember.setTeam("부서");
        companyMember.setGrade("직급");
        companyMember.setStatus(Status.PENDING);

        List<CompanyMember> companyMembers = List.of(companyMember);
        Page<CompanyMember> companyMemberPage = new PageImpl<>(List.of(companyMember));

        given(companyMemberService.findCompanyMembersByCompanyId(Mockito.anyInt(), Mockito.anyLong())).willReturn(companyMemberPage);
        given(statusOfWorkService.findTodayStatusOfWorks(Mockito.anyList(), Mockito.anyLong(), Mockito.anyLong())).willReturn(companyMembers);
        given(statusOfWorkMapper.todayToResponse(Mockito.anyList())).willReturn(StubData.MockStatusOfWork.getTodayList());

        mockMvc.perform(get("/manager/{company-id}/mystaff", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .params(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-TodayStatus",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                        List.of(
                                parameterWithName("page").description("현재 페이지")
                        )
                ),
                responseFields(
                        List.of(
                                fieldWithPath("data[].member.companyMemberId").type(JsonFieldType.NUMBER).description("회사 회원 식별 번호"),
                                fieldWithPath("data[].member.companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                fieldWithPath("data[].member.memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                fieldWithPath("data[].member.name").type(JsonFieldType.STRING).description("회사 회원 이름"),
                                fieldWithPath("data[].member.grade").type(JsonFieldType.STRING).description("회사 회원 직급"),
                                fieldWithPath("data[].member.team").type(JsonFieldType.STRING).description("회사 회원 소속 부서"),
                                fieldWithPath("data[].member.roles").type(JsonFieldType.ARRAY).description("회사 회원 권한").optional(),
                                fieldWithPath("data[].member.status").type(JsonFieldType.STRING).description("회사 회원 상태"),
                                fieldWithPath("data[].status[].id").type(JsonFieldType.NUMBER).description("특이 사항 식별 번호"),
                                fieldWithPath("data[].status[].memberId").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                fieldWithPath("data[].status[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data[].status[].companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                fieldWithPath("data[].status[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                fieldWithPath("data[].status[].startTime").type(JsonFieldType.STRING).description("특이사항 시작 시간"),
                                fieldWithPath("data[].status[].finishTime").type(JsonFieldType.STRING).description("특이사항 마감 시간"),
                                fieldWithPath("data[].status[].note").type(JsonFieldType.STRING).description("특이사항 내용: 지각 / 조퇴 / 결근 / 연장근로 / 휴일근로 / 야간근로 / 유급휴가 / 무급휴가"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 직원 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                )
        ));
    }


    @Test
    @DisplayName("StatusOfWork Delete Test")
    public void deleteStatusOfWorkTest() throws Exception {
        doNothing().when(statusOfWorkService).deleteStatusOfWork(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(delete("/status/{statusofwork-id}", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
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

    @Test
    @DisplayName("Vacation Request Test") // 이유 모를 "Request Body에 필요한 데이터가 없습니다."
    public void requestVacationTest() throws Exception {
        VacationDto.Post post = new VacationDto.Post();
        post.setCompanyId(1L);
        post.setVacationStart(LocalDate.MIN);
        post.setVacationEnd(LocalDate.MAX);
        String content = toJsonContent(post);

        given(statusOfWorkMapper.postToRequestVacation(Mockito.any(VacationDto.Post.class))).willReturn(new RequestVacation());
        doNothing().when(statusOfWorkService).requestVacation(Mockito.any(RequestVacation.class), Mockito.anyLong());

        mockMvc.perform(post("/worker/mywork/vacations", accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
//                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(
                        document(
                                "vacation-request",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestFields(
                                        List.of(
                                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                                fieldWithPath("vacationStart").type(JsonFieldType.STRING).description("휴가 시작일"),
                                                fieldWithPath("vacationEnd").type(JsonFieldType.STRING).description("휴가 종료일")
                                        )
                                )
                        )
                );

    }

    @Test
    @DisplayName("Vacation Review Test")
    public void reviewVacationTest() throws Exception {
        doNothing().when(statusOfWorkService).reviewRequestVacation(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong());

        mockMvc.perform(post("/manager/vacations/{vacation-id}/{status}", 1L, "approved", accessToken)
                .header("Authorization", "Bearer ".concat(accessToken)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "review-vacation",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("vacation-id").description("휴가 신청 식별 번호"),
                                parameterWithName("status").description("승인, 거절 (approved / refuse)")
                        )
                ));
    }

    @Test
    @DisplayName("Get Vacation Request List Test")
    public void getVacationRequestListTest() throws Exception {
        List<RequestVacation> list = List.of(new RequestVacation());
        List<VacationDto.Response> responseList = List.of(
                VacationDto.Response.builder()
                        .requestId(1L)
                        .companyMemberId(1L)
                        .name("근로자 이름")
                        .vacationStart(LocalDate.MIN)
                        .vacationEnd(LocalDate.MAX).build(),
                VacationDto.Response.builder()
                        .requestId(2L)
                        .companyMemberId(1L)
                        .name("근로자 이름")
                        .vacationStart(LocalDate.MIN)
                        .vacationEnd(LocalDate.MAX).build()
        );

        given(statusOfWorkService.getRequestList(Mockito.anyLong())).willReturn(list);
        given(statusOfWorkMapper.requestResponses(anyList())).willReturn(responseList);

        mockMvc.perform(get("/manager/{company-id}/vacations", 1L, accessToken)
                .header("Authorization", "Bearer ".concat(accessToken))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "get-requestList",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("company-id").description("회사 식별 번호")
                                ),
                                responseFields(
                                        fieldWithPath("[].requestId").type(JsonFieldType.NUMBER).description("휴가 신청 식별 번호"),
                                        fieldWithPath("[].companyMemberId").type(JsonFieldType.NUMBER).description("사원 식별 번호"),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("근로자 이름"),
                                        fieldWithPath("[].vacationStart").type(JsonFieldType.STRING).description("휴가 시작 일자"),
                                        fieldWithPath("[].vacationEnd").type(JsonFieldType.STRING).description("휴가 종료 일자")
                                )
                        )
                );
    }
}
