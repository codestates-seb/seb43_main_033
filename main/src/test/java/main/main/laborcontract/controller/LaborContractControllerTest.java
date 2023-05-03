package main.main.laborcontract.controller;

import main.main.helper.LaborContractHelper;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
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

@WebMvcTest(LaborContractController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
public class LaborContractControllerTest implements LaborContractHelper {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LaborContractService laborContractService;
    @MockBean
    private LaborContractMapper laborContractMapper;

    @Test
    @DisplayName("LaborContract Post Test")
    public void postLaborContractTest() throws Exception {

    }

    @Test
    @DisplayName("LaborContract Patch Test")
    public void patchLaborContractTest() throws Exception {

    }

    @Test
    @DisplayName("LaborContract Get Test")
    public void getLaborContractTest() throws Exception {

    }

    @Test
    @DisplayName("LaborContract Delete Test")
    public void deleteLaborContractTest() throws Exception {

    }
}
