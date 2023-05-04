package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface BankHelper extends ControllerHelper {
    String BANK_DEFAULT_URL = "/banks";
    String BANK_RESOURCE_ID = "/{bank-id}";
    String BANK_RESOURCE_URI = BANK_DEFAULT_URL + BANK_RESOURCE_ID;

    default List<ParameterDescriptor> getBankRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("bank-id").description("은행 식별 번호"));
    }
}