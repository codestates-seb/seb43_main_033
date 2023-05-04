package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface SalaryStatementHelper extends ControllerHelper {
    String SALARYSTATEMENT_DEFAULT_URL = "/salarystatements";
    String SALARYSTATEMENT_RESOURCE_ID = "/{salarystatement-id}";
    String SALARYSTATEMENT_RESOURCE_URI = SALARYSTATEMENT_DEFAULT_URL + SALARYSTATEMENT_RESOURCE_ID;

    default List<ParameterDescriptor> getMemberRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("salarystatement-id").description("급여명세서 식별 번호"));
    }
}
