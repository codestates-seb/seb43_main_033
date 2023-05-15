package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface MemberBankHelper extends ControllerHelper {
    String MEMBERBANK_DEFAULT_URL = "/memberbanks";
    String MEMBERBANK_RESOURCE_ID = "/{memberbank-id}";
    String MEMBERBANK_RESOURCE_URI = MEMBERBANK_DEFAULT_URL + MEMBERBANK_RESOURCE_ID;

    default List<ParameterDescriptor> getMemberBankRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("memberbank-id").description("계좌 정보 식별 번호"));
    }
}
