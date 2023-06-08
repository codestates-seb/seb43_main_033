package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface CompanyHelper extends ControllerHelper {
    String COMPANY_DEFAULT_URL = "/companies";
    String COMPANY_RESOURCE_ID = "/{company-id}";
    String COMPANY_RESOURCE_URI = COMPANY_DEFAULT_URL + COMPANY_RESOURCE_ID;


    String BUSINESS_DEFAULT_URL = "/companies/businessnumber";
    String MEMBER_RESOURCE_ID = "/{member-id}";
    String BUSINESS_RESOURCE_URL = BUSINESS_DEFAULT_URL + MEMBER_RESOURCE_ID;

    default List<ParameterDescriptor> getCompanyRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("company-id").description("회사 식별 번호"));
    }

    default List<ParameterDescriptor> getMemberRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("member-id").description("회원 식별 번호"));
    }
}
