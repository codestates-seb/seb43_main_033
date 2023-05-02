package main.main.laborcontract.mapper;

import javax.annotation.processing.Generated;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-02T14:51:03+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class LaborContractMapperImpl implements LaborContractMapper {

    @Override
    public LaborContract patchToLaborContract(LaborContractDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        LaborContract laborContract = new LaborContract();

        laborContract.setBasicSalary( requestBody.getBasicSalary() );
        laborContract.setStartTime( requestBody.getStartTime() );
        laborContract.setFinishTime( requestBody.getFinishTime() );
        laborContract.setInformation( requestBody.getInformation() );

        return laborContract;
    }
}
