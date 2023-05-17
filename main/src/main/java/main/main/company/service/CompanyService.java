package main.main.company.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.repository.CompanyRepository;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.salarystatement.entity.SalaryStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final MemberService memberService;
    private final CompanyMemberRepository companyMemberRepository;

    public Company createCompany(Company company) {

        return companyRepository.save(company);
    }

    public Company findCompany(String name) {
        return companyRepository.findByCompanyName(name).get();
    }

    public Page<Company> findCompanies(int page, int size) {
        return companyRepository.findAll(PageRequest.of(page, size, Sort.by("companyName").ascending()));
    }

    public Company findCompany(long companyId) {
        return findVerifiedCompany(companyId);
    }

    private Company findVerifiedCompany(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        Company findedCompany = optionalCompany.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND));
        return findedCompany;
    }

    private Company findVerifiedBusinessNumber(String businessNumber) {
        Optional<Company> optionalBusinessNumber = companyRepository.findByBusinessNumber(businessNumber);
        Company findedBusinessNumber = optionalBusinessNumber.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUSINESS_NOT_FOUND));
        return findedBusinessNumber;
    }


    public Company updateCompany(Company company, String businessNumber, long authenticationMemberId) {
        Company findedCompany = findVerifiedCompany(company.getCompanyId());

        checkPermission(authenticationMemberId, findedCompany);

        Optional.ofNullable(company.getCompanyName())
                .ifPresent(CompanyName -> findedCompany.setCompanyName(company.getCompanyName()));
        Optional.ofNullable(company.getCompanySize())
                .ifPresent(CompanySize -> findedCompany.setCompanySize(company.getCompanySize()));
        Optional.ofNullable(company.getAddress())
                .ifPresent(Address -> findedCompany.setAddress(company.getAddress()));
        Optional.ofNullable(company.getInformation())
                .ifPresent(Information -> findedCompany.setInformation(company.getInformation()));

        Optional.ofNullable(company.getBusinessNumber())
                .ifPresent(BusinessNumber -> findedCompany.setBusinessNumber(businessNumber));

        return companyRepository.save(findedCompany);
    }


    public void deleteCompany(long companyId, long authenticationMemberId) {
        Company findedCompany = findVerifiedCompany(companyId);

        checkPermission(authenticationMemberId, findedCompany);

        companyRepository.delete(findedCompany);
    }

    public BigDecimal[] salaryOfCompany(long companyId) {

        Company company = findVerifiedCompany(companyId);


        BigDecimal salaryOfCompanyThisMonth = BigDecimal.ZERO;
        BigDecimal salaryOfCompanyLastMonth = BigDecimal.ZERO;

        List<SalaryStatement> statements = company.getSalaryStatements();

        LocalDate localDate = LocalDate.now();
        int currentYear = localDate.getYear();
        int currentMonth = localDate.getMonthValue();
        int lastMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
        int lastYear = (currentMonth == 1) ? currentYear - 1 : currentYear;


        for (SalaryStatement statement : statements) {
            int statementYear = statement.getYear();
            int statementMonth = statement.getMonth();

            if (statementMonth == currentMonth && statementYear == currentYear) {
                salaryOfCompanyThisMonth = statement.getTotalSalary();
            }

            if (statementMonth == lastMonth && statementYear == lastYear) {
                salaryOfCompanyLastMonth = statement.getTotalSalary();
            }
        }
        return new BigDecimal[]{salaryOfCompanyThisMonth, salaryOfCompanyLastMonth};
    }


    public Boolean comapanyuploading(MultipartFile file, long companyId, String url, long authenticationMemberId) {

        Company company = findVerifiedCompany(companyId);

        checkPermission(authenticationMemberId, company);

        Boolean result = Boolean.TRUE;

        String dir = Long.toString(companyId);
        String extension = getExtension(file);

        String newFileName = dir + extension;


        try {
            File folder = new File("img" + File.separator + "회사_이미지" + File.separator + dir + File.separator + "회사_대표_이미지");
            File[] files = folder.listFiles();
            if (!folder.exists()) {
                folder.mkdirs();
            } else if (files != null) {
                for (File file1 : files) {
                    file1.delete();
                }
            }
            File destination = new File(folder.getAbsolutePath(), newFileName);
            file.transferTo(destination);
            result = Boolean.FALSE;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public Boolean businessuploading(MultipartFile file, long companyId, String url, long authenticationMemberId) {

        Company company = findVerifiedCompany(companyId);

        checkPermission(authenticationMemberId, company);

        Boolean result = Boolean.TRUE;

        String dir = Long.toString(companyId);
        String extension = getExtension(file);

        String newFileName = dir + extension;


        try {
            File folder = new File("img" + File.separator + "회사_이미지" + File.separator + dir + File.separator + "사업자_등록증_이미지");
            File[] files = folder.listFiles();
            if (!folder.exists()) {
                folder.mkdirs();
            } else if (files != null) {
                for (File file1 : files) {
                    file1.delete();
                }
            }
            File destination = new File(folder.getAbsolutePath(), newFileName);
            file.transferTo(destination);
            result = Boolean.FALSE;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    public String getExtension(MultipartFile file) {
        return Optional.ofNullable(file)
                .map(MultipartFile::getOriginalFilename)
                .map(name -> name.substring(name.lastIndexOf(".")).toLowerCase())
                .orElse("default_extension");

    }


    public List<Map<String, Object>> findBusinessNumbersByRole(long memberId, long authenticationMemberId) {
        Member member = memberService.findMember(memberId);
        List<Map<String, Object>> business = new ArrayList<>();

        if (member.getCompanyMembers().isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND);
        }

        for (CompanyMember companyMember : member.getCompanyMembers()) {
            checkPermission(authenticationMemberId, companyMember.getCompany());

            Company company = companyMember.getCompany();
            String businessNumber = company.getBusinessNumber();

            Map<String, Object> businessData = new HashMap<>();
            businessData.put("companyId", company.getCompanyId());
            businessData.put("companyName", company.getCompanyName());
            businessData.put("businessNumber", businessNumber != null ? businessNumber : "아직 인증된 사업자등록증이 없습니다.");

            business.add(businessData);
        }

        if (business.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        return business;
    }


    private void checkPermission(long authenticationMemberId, Company company) {
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (companyMember == null || !companyMember.getRoles().contains("MANAGER")) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }




//    public List<Map<String, Object>> statusOfWorkers(long companyId) {
//        Company company = findCompany(companyId);
//        CompanyMember companyMember = findCompanyMember();
//        List<Map<String, Object>> status = new ArrayList<>();
//
//
//
//        return ;
//    }
}

