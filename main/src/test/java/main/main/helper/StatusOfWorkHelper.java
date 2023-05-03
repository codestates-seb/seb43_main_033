package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface StatusOfWorkHelper extends ControllerHelper {
    String STATUSOFWORK_DEFAULT_URL = "/statusofwork";
    String STATUSOFWORK_RESOURCE_ID = "/{statusofwork-id}";
    String STATUSOFWORK_RESOURCE_URI = STATUSOFWORK_DEFAULT_URL + STATUSOFWORK_RESOURCE_ID;

    default List<ParameterDescriptor> getMemberRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("statusofwork-id").description("특이사항 식별 번호"));
    }
}
