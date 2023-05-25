package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface MemberBankHelper extends ControllerHelper {
    String MEMBERBANK_DEFAULT_URL = "/memberbanks";
    String MEMBER_DEFAULT_URL = "/member";
    String MEMBERBANK_RESOURCE_ID = "/{memberbank-id}";

    String MEMBER_RESOURCE_ID = "/{member-id}";
    String MEMBERBANK_RESOURCE_URI = MEMBERBANK_DEFAULT_URL + MEMBERBANK_RESOURCE_ID;

    String MEMBERBANK_MEMBER_URI = MEMBERBANK_DEFAULT_URL+ MEMBER_DEFAULT_URL + MEMBER_RESOURCE_ID;

    default List<ParameterDescriptor> getMemberBankRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("memberbank-id").description("계좌 정보 식별 번호"));
    }
}
