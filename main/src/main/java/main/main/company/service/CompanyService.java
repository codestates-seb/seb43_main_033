package main.main.company.service;

import lombok.RequiredArgsConstructor;
import main.main.auth.utils.CustomAuthorityUtils;
import main.main.company.entity.Company;
import main.main.company.repository.CompanyRepository;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.statusofwork.entity.Vacation;
import main.main.utils.AwsS3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final MemberService memberService;
    private final CompanyMemberRepository companyMemberRepository;
    private final AwsS3Service awsS3Service;
    private final CustomAuthorityUtils authorityUtils;

    public Company createCompany(Company company, long authenticationMemberId) {
        Member member = memberService.findMember(authenticationMemberId);
        company.setMemberId(member.getMemberId());
        Company createdCompany = companyRepository.save(company);

        CompanyMember companyMember = new CompanyMember();
        companyMember.setCompany(createdCompany);
        companyMember.setMember(member);
        companyMember.setRoles(Collections.singletonList("ADMIN"));
        companyMember.setVacation(new Vacation());

        companyMemberRepository.save(companyMember);
        return createdCompany;
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

        checkManager(authenticationMemberId, findedCompany);

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
        checkManager(authenticationMemberId, findedCompany);

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


    private void checkPermission(long authenticationMemberId, Company company) { // 본인이거나 매니저일경우 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyAndMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (!isAuthorizedMember(member, authenticationMemberId) && !isManager(companyAndMember)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkIdentity(long authenticationMemberId) { // 본인일때만 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        Member member = memberService.findMember(authenticationMemberId);
        if (!isAuthorizedMember(member, authenticationMemberId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkManager(long authenticationMemberId, Company company) { // 매니저일때만 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyAndMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (!isManager(companyAndMember)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }


    private boolean isAuthorizedMember(Member member, long authenticationMemberId) {
        return member != null && member.getMemberId().equals(authenticationMemberId);
    }

    private boolean isManager(CompanyMember companyMember) {
        return companyMember != null && companyMember.getRoles().contains("MANAGER") || companyMember.getRoles().contains("ADMIN");
    }

}



