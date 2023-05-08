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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final MemberService memberService;
    private final CompanyService companyService;

    public void creatLaborContract(LaborContract laborContract, MultipartFile file) {
        Member member = memberService.findMember(laborContract.getMember().getMemberId());
        Company company = companyService.findCompany(laborContract.getCompany().getCompanyId());

        laborContract.setMember(member);
        laborContract.setCompany(company);

        LaborContract savedLaborContract = laborContractRepository.save(laborContract);

        uploadFile(file, savedLaborContract);
    }



    public void updateLaborContract(long laborContractId, LaborContract laborContract, MultipartFile file) {
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

        uploadFile(file, findedLaborContract);
    }

    public LaborContract findLaborContract(long laborContractId) {
        return findVerifiedContract(laborContractId);
    }

    public LaborContract findLaborContractForSalaryStatement(Member member, Company company, int year, int month) {
        LocalDateTime thisMonth = LocalDateTime.of(year, month, 1, 0, 0);
        Optional<LaborContract> optionalLaborContract = laborContractRepository.findLaborContractByMemberAndCompanyAndEndOfContractAfter(member, company, thisMonth);
        LaborContract laborContract = optionalLaborContract.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_FOR_SALARY_NOT_FOUND));

        return  laborContract;
    }

    public void deleteLaborContract(long laborContractId) {
        laborContractRepository.delete(findVerifiedContract(laborContractId));
    }

    private LaborContract findVerifiedContract(long laborContractId) {
        Optional<LaborContract> optionalLaborContract = laborContractRepository.findById(laborContractId);
        LaborContract laborContract = optionalLaborContract.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LABORCONTRACT_NOT_FOUND));

        return laborContract;
    }

    private static void uploadFile(MultipartFile file, LaborContract savedLaborContract) {
        if (file != null) {
            String dir = Long.toString(savedLaborContract.getId());
            String extension = Optional.ofNullable(file)
                    .map(MultipartFile::getOriginalFilename)
                    .map(name -> name.substring(name.lastIndexOf(".")).toLowerCase())
                    .orElse("default_extension");

            String newFileName = dir + extension;

            try {
                File folder = new File("img" + File.separator + "근로계약서" + File.separator + dir);
                File[] files = folder.listFiles();
                if (!folder.exists()) {
                    folder.mkdirs();
                } else if (files != null) {
                    for (File file1 : files) {
                        file1.delete();
                    }
                }
                File destination = new File(folder.getAbsolutePath() , newFileName);

                file.transferTo(destination);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}