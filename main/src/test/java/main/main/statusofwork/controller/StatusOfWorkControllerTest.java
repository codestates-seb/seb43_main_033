package main.main.statusofwork.controller;

import main.main.helper.StatusOfWorkHelper;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

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

    }

    @Test
    @DisplayName("StatusOfWork Patch Test")
    public void patchStatusOfWorkTest() throws Exception {

    }

    @Test
    @DisplayName("StatusOfWork Get Test")
    public void getStatusOfWorkTest() throws Exception {

    }

    @Test
    @DisplayName("StatusOfWork Delete Test")
    public void deleteStatusOfWorkTest() throws Exception {

    }
}
