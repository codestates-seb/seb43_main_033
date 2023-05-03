package main.main.salarystatement.controller;

import main.main.helper.SalaryStatementHelper;
import main.main.salarystatement.mapper.SalaryStatementMapper;
import main.main.salarystatement.service.SalaryStatementService;
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

    }

    @Test
    @DisplayName("SalaryStatement Get Test")
    public void getSalaryStatementTest() throws Exception {

    }

    @Test
    @DisplayName("SalaryStatement Delete Test")
    public void deleteSalaryStatementTest() throws Exception {

    }
}
