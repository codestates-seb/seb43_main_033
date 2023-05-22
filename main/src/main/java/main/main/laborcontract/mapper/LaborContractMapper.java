package main.main.laborcontract.mapper;

import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LaborContractMapper {
    default LaborContract postToLaborContract(LaborContractDto.Post requestBody) {
        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompanyMemberId(requestBody.getCompanyMemberId());
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        LaborContract laborContract = new LaborContract();
        laborContract.setCompanyMember(companyMember);
        laborContract.setCompany(company);
        laborContract.setBasicSalary(requestBody.getBasicSalary());
        laborContract.setStartOfContract(requestBody.getStartOfContract());
        laborContract.setEndOfContract(requestBody.getEndOfContract());
        laborContract.setStartTime(requestBody.getStartTime());
        laborContract.setFinishTime(requestBody.getFinishTime());
        laborContract.setInformation(requestBody.getInformation());

        return laborContract;
    }

    default LaborContract patchToLaborContract(LaborContractDto.Patch requestBody) {
        Company company = new Company();
        LaborContract laborContract = new LaborContract();
        laborContract.setCompany(company);
        laborContract.setBasicSalary(requestBody.getBasicSalary());
        laborContract.setStartTime(requestBody.getStartTime());
        laborContract.setFinishTime(requestBody.getFinishTime());
        laborContract.setInformation(requestBody.getInformation());

        return laborContract;
    }

    default LaborContractDto.Response laborContractToResponse(LaborContract laborContract) {

        return LaborContractDto.Response.builder()
                .laborContactId(laborContract.getId())
                .memberName(laborContract.getMember().getName())
                .companyName(laborContract.getCompany().getCompanyName())
                .bankName(laborContract.getBankName())
                .accountNumber(laborContract.getAccountNumber())
                .accountHolder(laborContract.getAccountHolder())
                .basicSalary(laborContract.getBasicSalary())
                .startOfContract(laborContract.getStartOfContract())
                .endOfContract(laborContract.getEndOfContract())
                .startTime(laborContract.getStartTime())
                .finishTime(laborContract.getFinishTime())
                .information(laborContract.getInformation())
                .uri(laborContract.getLaborContractUri())
                .build();
    }

    default List<LaborContractDto.Response> laborContractsToResponses(List<LaborContract> laborContracts) {
        return laborContracts.stream().map(laborContract -> laborContractToResponse(laborContract)).collect(Collectors.toList());
    }
}
