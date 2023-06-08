package main.main.helper;

import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface LaborContractHelper extends ControllerHelper {
    String LABORCONTRACT_DEFAULT_URL = "/laborcontracts";
    String LABORCONTRACT_RESOURCE_ID = "/{laborcontract-id}";
    String LABORCONTRACT_RESOURCE_URI = LABORCONTRACT_DEFAULT_URL + LABORCONTRACT_RESOURCE_ID;
    String LABORCONTRACT_IMAGE_URI = LABORCONTRACT_RESOURCE_URI + "/file";

    default List<ParameterDescriptor> getRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("laborcontract-id").description("근로계약서 식별 번호"));
    }
}
