package main.main.bank.controller;

import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import main.main.bank.mapper.BankMapper;
import main.main.bank.service.BankService;
import main.main.helper.BankHelper;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankControllerTest implements BankHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @MockBean
    private BankMapper bankMapper;

    @Test
    @DisplayName("Bank Get Test")
    public void getBankTest() throws Exception {

        BankDto.Response response = StubData.MockBank.getBankResponse();

        given(bankService.findBank(Mockito.anyLong())).willReturn(new Bank());
        given(bankMapper.bankToBankResponse(Mockito.any(Bank.class))).willReturn(response);


        ResultActions actions =
                mockMvc.perform(getRequestBuilder(BANK_RESOURCE_URI, 1L));

        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-bank",
                        pathParameters(
                                parameterWithName("bank-id").description("은행 식별 번호")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("bankId").type(JsonFieldType.NUMBER).description("은행 식별 번호").optional(),
                                        fieldWithPath("bankName").type(JsonFieldType.STRING).description("은행명").optional(),
                                        fieldWithPath("bankCode").type(JsonFieldType.STRING).description("은행 코드").optional()
                                )
                        )
                ));
    }
}
