package main.main.laborcontract.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.companymember.service.CompanyMemberService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.repository.LaborContractRepository;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.memberbank.entity.MemberBank;
import main.main.utils.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final MemberService memberService;
    private final CompanyService companyService;
    private final CompanyMemberService companyMemberService;
    private final AwsS3Service awsS3Service;

    public void creatLaborContract(LaborContract laborContract, MultipartFile file, long authenticationMemberId) {
        Company company = companyService.findCompany(laborContract.getCompany().getCompanyId());
        CompanyMember companyMember = companyMemberService.findCompanyMember(laborContract.getCompanyMember().getCompanyMemberId());

        if (!companyMember.getCompany().getCompanyId().equals(company.getCompanyId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_STATUS);
        }

        Member member = companyMember.getMember();
        MemberBank memberBank = member.getMemberBanks().stream()
                .filter(mainMemberBank -> mainMemberBank.isMainAccount())
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBERBANK_ONLY_ONE));

        checkPermission(authenticationMemberId, company);

        laborContract.setMember(member);
        laborContract.setCompany(company);
        laborContract.setCompanyMember(companyMember);
        laborContract.setBankName(memberBank.getBank().getBankGroup().getBankName());
        laborContract.setAccountNumber(memberBank.getAccountNumber());
        laborContract.setAccountHolder(memberBank.getMember().getName());
        if (file != null) {
            String[] uriList = awsS3Service.uploadFile(file);
            laborContract.setLaborContractUri(uriList[0]);
            laborContract.setPureUri(uriList[1]);
        }
        laborContractRepository.save(laborContract);
    }

    public void updateLaborContract(long laborContractId, LaborContract laborContract, MultipartFile file, long authenticationMemberId) {
        LaborContract findedLaborContract = findVerifiedContract(laborContractId);

        if (!laborContract.getCompany().getCompanyId().equals(findedLaborContract.getCompany().getCompanyId())) {
           throw new BusinessLogicException(ExceptionCode.INVALID_STATUS);
        }
        checkPermission(authenticationMemberId, findedLaborContract.getCompany());

        Optional.ofNullable(laborContract.getBasicSalary())
                .ifPresent(basicSalary -> findedLaborContract.setBasicSalary(basicSalary));
        Optional.ofNullable(laborContract.getStartOfContract())
                .ifPresent(startOffContract -> findedLaborContract.setStartOfContract(startOffContract));
        Optional.ofNullable(laborContract.getEndOfContract())
                .ifPresent(endOfContract -> findedLaborContract.setEndOfContract(endOfContract));
        Optional.ofNullable(laborContract.getStartTime())
                .ifPresent(startTime -> findedLaborContract.setStartTime(startTime));
        Optional.ofNullable(laborContract.getFinishTime())
                .ifPresent(finishTime -> laborContract.setFinishTime(finishTime));
        Optional.ofNullable(laborContract.getInformation())
                .ifPresent(information -> findedLaborContract.setInformation(information));
        if (file != null) {
            awsS3Service.deleteFile(findedLaborContract.getPureUri());
            String[] uriList = awsS3Service.uploadFile(file);
            findedLaborContract.setLaborContractUri(uriList[0]);
            findedLaborContract.setPureUri(uriList[1]);
        }

        laborContractRepository.save(findedLaborContract);
    }

    public LaborContract findLaborContract(long laborContractId, long authenticationMemberId) {
        LaborContract findedLaborContract = findVerifiedContract(laborContractId);

        checkGetPermission(authenticationMemberId, findedLaborContract);

        return findedLaborContract;
    }

    public List<LaborContract> findLaborContractList(long companyMemberId, long authenticationMemberId) {
        CompanyMember companyMember = companyMemberService.findCompanyMember(companyMemberId);
        List<LaborContract> laborContracts = laborContractRepository.findLaborContractByCompanyMember(companyMember);

        laborContracts.stream().forEach(laborContract -> checkGetPermission(authenticationMemberId, laborContract));

        return laborContracts;
    }

    public List<LaborContract> findLaborContract(long authenticationMemberId) {
        Member member = memberService.findMember(authenticationMemberId);

        return member.getLaborContracts();
    }

    public LaborContract findLaborContractForSalaryStatement(Member member, Company company, int year, int month) {
        LocalDateTime thisMonth = LocalDateTime.of(year, month, 1, 0, 0);
        Optional<LaborContract> optionalLaborContract = laborContractRepository.findLaborContractByMemberAndCompanyAndEndOfContractAfter(member, company, thisMonth);
        LaborContract laborContract = optionalLaborContract.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_FOR_SALARY_NOT_FOUND));

        return  laborContract;
    }

    public void deleteLaborContract(long laborContractId, long authenticationMemberId) {
        checkPermission(authenticationMemberId, findVerifiedContract(laborContractId).getCompany());

        laborContractRepository.delete(findVerifiedContract(laborContractId));
    }


    private LaborContract findVerifiedContract(long laborContractId) {
        Optional<LaborContract> optionalLaborContract = laborContractRepository.findById(laborContractId);
        LaborContract laborContract = optionalLaborContract.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_NOT_FOUND));

        return laborContract;
    }

    private void checkPermission(long authenticationMemberId, Company company) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, company);
        if (!companyMember.getRoles().contains("MANAGER")) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkGetPermission(long authenticationMemberId, LaborContract findedLaborContract) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member manager = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(manager, findedLaborContract.getCompany());
        if (!companyMember.getRoles().contains("MANAGER") || findedLaborContract.getMember().getMemberId() != authenticationMemberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}
