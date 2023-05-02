package main.main.laborcontract.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.repository.LaborContractRepository;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final MemberService memberService;
    private final CompanyService companyService;

    public void creatLaborContract(LaborContract laborContract) {
        Member member = memberService.findMember(laborContract.getMember().getMemberId());
        Company company = companyService.findCompany(laborContract.getCompany().getCompanyId());

        laborContract.setMember(member);
        laborContract.setCompany(company);

        laborContractRepository.save(laborContract);
    }

    public void updateLaborContract(long laborContractId, LaborContract laborContract) {
        LaborContract findedLaborContract = findVerifiedContract(laborContractId);

        Optional.ofNullable(laborContract.getBasicSalary())
                .ifPresent(basicSalary -> findedLaborContract.setBasicSalary(basicSalary));
        Optional.ofNullable(laborContract.getStartTime())
                .ifPresent(startTime -> findedLaborContract.setStartTime(startTime));
        Optional.ofNullable(laborContract.getFinishTime())
                .ifPresent(finishTime -> laborContract.setFinishTime(finishTime));
        Optional.ofNullable(laborContract.getInformation())
                .ifPresent(information -> findedLaborContract.setInformation(information));

        laborContractRepository.save(findedLaborContract);
    }

    public LaborContract findLaborContract(long laborContractId) {
        return findVerifiedContract(laborContractId);
    }

    public void deleteLaborContract(long laborContractId) {
        laborContractRepository.delete(findVerifiedContract(laborContractId));
    }

    private LaborContract findVerifiedContract(long laborContractId) {
        Optional<LaborContract> optionalLaborContract = laborContractRepository.findById(laborContractId);
        LaborContract laborContract = optionalLaborContract.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_NOT_FOUND));

        return laborContract;
    }
}
