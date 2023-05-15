package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface CompanyMemberHelper extends ControllerHelper {
    String COMPANYMEMBER_DEFAULT_URL = "/companymembers";
    String COMPANYMEMBER_RESOURCE_ID = "/{companymember-id}";
    String COMPANYMEMBER_RESOURCE_URI = COMPANYMEMBER_DEFAULT_URL + COMPANYMEMBER_RESOURCE_ID;

    default List<ParameterDescriptor> getCompanyMemberRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("companymember-id").description("회사 사원 식별 번호"));
    }
}
