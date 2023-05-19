package main.main.company.controller;

import main.main.company.service.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ImageController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    @DisplayName("Post Image Test")
    public void postImageTest() throws Exception {

        String type = "company_image";
        long companyId = 1L;
        long authenticatedMemberId = 123L;

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "image/png".getBytes());

        String mockUri = "mock_image_uri";
        doNothing().when(imageService).postImage(type, file, companyId, authenticatedMemberId);
        given(imageService.getImage(type, companyId)).willReturn(mockUri);

        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/companies/{type}/{company-id}", type, companyId)
                                .file(file)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("companyId").value(companyId))
                .andExpect(jsonPath("uri").value(mockUri))
                .andDo(print())
                .andDo(document("post-image-upload",
                        pathParameters(
                                parameterWithName("type").description("업로드 이미지 유형"),
                                parameterWithName("company-id").description("회사 식별 번호")
                        ),
                        requestParts(
                                partWithName("file").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                fieldWithPath("uri").type(JsonFieldType.STRING).description("업로드 이미지 URI")
                        )
                ));
    }



    @Test
    @DisplayName("Get Image Test")
    public void getImageUriTest() throws Exception {
        String type = "company_image";
        Long companyId = 1L;

        String mockUri = "mock_image_uri";
        given(imageService.getImage(type, companyId)).willReturn(mockUri);

        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/companies/{type}/{company-id}", type, companyId))

                .andExpect(status().isOk())
                .andExpect(content().json("{\"uri\":\"mock_image_uri\"}"))
                .andDo(print())
                .andDo(document("get-image-uri",
                        pathParameters(
                                parameterWithName("type").description("업로드 이미지 유형"),
                                parameterWithName("company-id").description("회사 식별 번호")
                        ),
                        responseFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                fieldWithPath("uri").type(JsonFieldType.STRING).description("업로드 이미지 URI")
                        )
                ));
    }






    @Test
    @DisplayName("Patch Image Test")
    public void patchImageUploadTest() throws Exception {

        String type = "company_image";
        Long companyId = 1L;
        Long authenticatedMemberId = 123L;

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "image/png".getBytes());

        String mockUri = "mock_image_uri";
        doNothing().when(imageService).patchImage(type, file, companyId, authenticatedMemberId);
        given(imageService.getImage(type, companyId)).willReturn(mockUri);

        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/companies/{type}/{company-id}", type, companyId)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-image-upload",
                        pathParameters(
                                parameterWithName("type").description("업로드 이미지 유형"),
                                parameterWithName("company-id").description("회사 식별 번호")
                        ),
                        requestParts(
                                partWithName("file").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 식별 번호"),
                                fieldWithPath("uri").type(JsonFieldType.STRING).description("업로드 이미지 URI")
                        )
                ));
    }

    @Test
    @DisplayName("Delete Image Test")
    public void deleteImageUploadTest() throws Exception {

        String type = "company_image";
        Long companyId = 1L;
        Long authenticatedMemberId = 123L;

        doNothing().when(imageService).deleteImage(type, companyId, authenticatedMemberId);
        mockMvc.perform(RestDocumentationRequestBuilders.multipart("/companies/{type}/{company-id}", type, companyId)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-image-upload",
                        pathParameters(
                                parameterWithName("type").description("업로드 이미지 유형"),
                                parameterWithName("company-id").description("회사 식별 번호")
                        )
                ));
    }

}

