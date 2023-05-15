package main.main.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.main.auth.jwt.JwtTokenizer;
import main.main.member.dto.MemberDto;
import main.main.member.entity.Member;
import main.main.member.mapper.MemberMapper;
import main.main.member.service.MemberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@ExtendWith({RestDocumentationExtension.class})
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    private String accessToken;

    @BeforeAll
    public void init() {
        accessToken = getValidAccessToken(jwtTokenizer.getSecretKey());

    }

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

//    @BeforeEach
//    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider) {
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(documentationConfiguration(restDocumentationContextProvider))
//                .build();
//    }

    public static String getValidAccessToken(String secretKey) {
        JwtTokenizer jwtTokenizer = new JwtTokenizer();
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1L);

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        Date expiration = calendar.getTime();

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    static OperationRequestPreprocessor getRequestPreProcessor() {
        return preprocessRequest(prettyPrint());
    }

    static OperationResponsePreprocessor getResponsePreProcessor() {
        return preprocessResponse(prettyPrint());
    }


    @Test
    @DisplayName("멤버 생성 테스트 성공")
    public void createMemberTest() throws Exception {
        // given
        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());

        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "\"name\": \"test\",\n" +
                                        "\"phoneNumber\": \"010-1111-1111\",\n" +
                                        "\"email\": \"test@gmail.com\",\n" +
                                        "\"password\": \"1234\",\n" +
                                        "\"residentNumber\": \"1111-1111\",\n" +
                                        "\"grade\": \"직원\",\n" +
                                        "\"address\": \"서울특별시 관악구 신림동 신림역\"\n" +
                                        "}")
                );
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(Matchers.startsWith("/members"))))
                .andDo(print())
                .andDo(document("회원 생성",
                        requestFields(
//                                fieldWithPath("memberId").description("회원 식별 번호"),
//                                fieldWithPath("roles").description("회원 권한"),
//                                fieldWithPath("company").description("회원 소속 회사 식별 번호"),
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("phoneNumber").description("회원 전화번호"),
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호"),
                                fieldWithPath("residentNumber").description("회원 주민등록번호"),
                                fieldWithPath("grade").description("회원 직급"),
                                fieldWithPath("address").description("회원 주소")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("/members/{member-id}")
                        )
                ));
    }

    @Test
    @DisplayName("회원 조회 테스트 성공")
    public void findMemberTest() throws Exception {

        MemberDto.Response response = MemberDto.Response.builder()
                .memberId(1L)
                .name("test")
                .phoneNumber("010-1111-1111")
                .email("test@gmail.com")
                .residentNumber("001111-1111111")
                .address("서울특별시 관악구 신림동 신림역")
                .position("STAFF")
                .build();


        // given
        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);


        ResultActions actions =
                mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{memberId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.memberId").value(response.getMemberId()))
//                .andExpect(jsonPath("$.name").value(response.getName()))
//                .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
//                .andExpect(jsonPath("$.email").value(response.getEmail()))
//                .andExpect(jsonPath("$.residentNumber").value(response.getResidentNumber()))
//                .andExpect(jsonPath("$.address").value(response.getAddress()))
//                .andDo(print())
//                .andDo(document("회원 조회",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 식별 번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("memberId").description("회원 식별 번호"),
////                                fieldWithPath("roles").description("회원 권한"),
////                                fieldWithPath("company").description("회원 소속 회사 식별 번호"),
//                                fieldWithPath("name").description("회원 이름"),
//                                fieldWithPath("phoneNumber").description("회원 전화번호"),
//                                fieldWithPath("email").description("회원 이메일"),
//                                fieldWithPath("residentNumber").description("회원 주민등록번호"),
//                                fieldWithPath("grade").description("회원 직급"),
//                                fieldWithPath("address").description("회원 주소"),
//                                fieldWithPath("position").description("회원 직급"),
//                                fieldWithPath("roles").description("회원 권한")
//                        )
//                ));
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    public void updateMemberTest() throws Exception {

        MemberDto.Patch patch = MemberDto.Patch.builder()
                .memberId(1L)
                .companyId(1L)
                .name("test")
                .phoneNumber("010-2222-3333")
                .email("test@gmail.com")
                .password("1234")
                .residentNumber("001111-1111111")
                .grade("매니저")
                .address("서울특별시 금천구 독산동 독산역")
                .position("MEMBER")
                .build();

        Member updatedMember = Member.builder()
                .memberId(1L)
                .name(patch.getName())
                .phoneNumber(patch.getPhoneNumber())
                .email(patch.getEmail())
                .password(patch.getPassword())
                .residentNumber(patch.getResidentNumber())
                .address(patch.getAddress())
                .build();

        given(memberService.findMember(Mockito.anyLong()))
                .willReturn(new Member());
        given(memberService.updateMember(Mockito.any(Member.class)))
                .willReturn(updatedMember);
        given(mapper.responserPatchToMember(Mockito.any(MemberDto.Patch.class), Mockito.anyLong()))
                .willReturn(updatedMember);

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.patch("/members/{member-id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(patch))
                                .accept(MediaType.APPLICATION_JSON)
                );
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("회원 수정",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestFields(
                                        List.of(
                                                fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                                        .description("회원 식별 번호"),
                                                fieldWithPath("name").type(JsonFieldType.STRING)
                                                        .description("회원 이름"),
                                                fieldWithPath("companyId").type(JsonFieldType.NUMBER)
                                                        .description("회사 식별 번호"),
                                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                                        .description("회원 전화번호"),
                                                fieldWithPath("email").type(JsonFieldType.STRING)
                                                        .description("회원 이메일"),
                                                fieldWithPath("password").type(JsonFieldType.STRING)
                                                        .description("회원 비밀번호"),
                                                fieldWithPath("residentNumber").type(JsonFieldType.STRING)
                                                        .description("회원 주민등록번호"),
                                                fieldWithPath("grade").type(JsonFieldType.STRING)
                                                        .description("회원 ??"),
                                                fieldWithPath("address").type(JsonFieldType.STRING)
                                                        .description("회원 주소"),
                                                fieldWithPath("position").type(JsonFieldType.STRING)
                                                        .description("회원 직급")
                                        )
                                )
                ));
    }

    @Test
    @DisplayName("회원 삭제 성공")
    public void deleteMemberTest() throws Exception {
        long memberId = 1L;
        long authenticationMemberId = 2L;
        doNothing().when(memberService).deleteMember(memberId, authenticationMemberId);

        mockMvc.perform(delete("/members/{member-id}", 1L)
                        .header("Authorization", "Bearer" .concat(accessToken)))
                .andExpect(status().isNoContent())
                .andDo(document("회원 삭제",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("삭제할 회원의 ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 인증 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("이미지 업로드 성공")
    public void imageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Image Test".getBytes()
        );
        MockMvc mockMvc =
                MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc.perform(multipart("/members/upload/{member-id}", 1L).file(file))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    }




