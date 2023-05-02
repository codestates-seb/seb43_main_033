package main.main.laborcontract.mapper;

import main.main.company.entity.Company;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LaborContractMapper {
    default LaborContract postToLaborContract(LaborContractDto.Post requestBody) {
        Member member = new Member();
        member.setMemberId(requestBody.getMemberId());
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        LaborContract laborContract = new LaborContract();
        laborContract.setMember(member);
        laborContract.setCompany(company);
        laborContract.setBasicSalary(requestBody.getBasicSalary());
        laborContract.setStartTime(requestBody.getStartTime());
        laborContract.setFinishTime(requestBody.getFinishTime());
        laborContract.setInformation(requestBody.getInformation());

        return laborContract;
    }

    LaborContract patchToLaborContract(LaborContractDto.Patch requestBody);

    default LaborContractDto.Response laborContractToResponse(LaborContract laborContract) {

        return LaborContractDto.Response.builder()
                .memberName(laborContract.getMember().getName())
                .companyName(laborContract.getCompany().getCompanyName())
                .basicSalary(laborContract.getBasicSalary())
                .startTime(laborContract.getStartTime())
                .finishTime(laborContract.getFinishTime())
                .information(laborContract.getInformation())
                .build();
    }
}
