package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface CompanyHelper extends ControllerHelper {
    String COMPANY_DEFAULT_URL = "/companies";
    String COMPANY_SALARY_URL = "/salary";
    String COMPANY_RESOURCE_ID = "/{company-id}";
    String COMPANY_RESOURCE_URI = COMPANY_DEFAULT_URL + COMPANY_RESOURCE_ID;
    String COMPANY_SALARY_URI = COMPANY_DEFAULT_URL + COMPANY_SALARY_URL + COMPANY_RESOURCE_ID;


    default List<ParameterDescriptor> getCompanyRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("company-id").description("회사 식별 번호"));
    }
}
