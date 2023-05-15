package main.main.laborcontract.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.repository.LaborContractRepository;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.memberbank.entity.MemberBank;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final MemberService memberService;
    private final CompanyService companyService;

    public void creatLaborContract(LaborContract laborContract, MultipartFile file, long authenticationMemberId) {
        Member member = memberService.findMember(laborContract.getMember().getMemberId());
        Company company = companyService.findCompany(laborContract.getCompany().getCompanyId());
        MemberBank memberBank = member.getMemberBanks().stream()
                .filter(mainMemberBank -> mainMemberBank.isMainAccount())
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBERBANK_ONLY_ONE));

        checkPermission(authenticationMemberId, company);

        laborContract.setMember(member);
        laborContract.setCompany(company);
        laborContract.setBankName(memberBank.getBank().getBankGroup().getBankName());
        laborContract.setAccountNumber(memberBank.getAccountNumber());
        laborContract.setAccountHolder(memberBank.getMember().getName());

        LaborContract savedLaborContract = laborContractRepository.save(laborContract);

        uploadFile(file, savedLaborContract);
    }

    public void updateLaborContract(long laborContractId, LaborContract laborContract, MultipartFile file, long authenticationMemberId) {
        LaborContract findedLaborContract = findVerifiedContract(laborContractId);

        checkPermission(authenticationMemberId, findedLaborContract.getCompany());

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

    public LaborContract findLaborContract(long laborContractId, long authenticationMemberId) {
        LaborContract findedLaborContract = findVerifiedContract(laborContractId);

        checkGetPermission(authenticationMemberId, findedLaborContract);

        return findedLaborContract;
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

    public HashMap<byte[], String> getImage(long laborContractId, long authenticationMemberId) throws IOException {
        checkGetPermission(authenticationMemberId, findVerifiedContract(laborContractId));

        String dir = Long.toString(laborContractId);
        File folder = new File("img" + File.separator + "근로계약서" + File.separator + dir);
        File[] files = folder.listFiles();
        String extension = "";
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                    extension = fileName.substring(dotIndex + 1);
                    break;
                }
            }
        }
        File imageFile = new File(folder, dir + "." + extension);
        InputStream inputStream = new FileInputStream(imageFile);
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();

        HashMap<byte[], String> result = new HashMap<>();
        result.put(imageByteArray, extension);

        return result;
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
