package main.main.company.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.repository.CompanyRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

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

    public Company findVerifiedCompany(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        Company findedCompany = optionalCompany.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND));
        return findedCompany;
    }


    public Company updateCompany(Company company) {
        Company findedCompany = findVerifiedCompany(company.getCompanyId());

        Optional.ofNullable(company.getCompanyName())
                .ifPresent(CompanyName -> findedCompany.setCompanyName(company.getCompanyName()));
        Optional.ofNullable(company.getCompanySize())
                .ifPresent(CompanySize -> findedCompany.setCompanySize(company.getCompanySize()));
        Optional.ofNullable(company.getBusinessNumber())
                .ifPresent(BusinessNumber -> findedCompany.setBusinessNumber(company.getBusinessNumber()));
        Optional.ofNullable(company.getAddress())
                .ifPresent(Address -> findedCompany.setAddress(company.getAddress()));
        Optional.ofNullable(company.getInformation())
                .ifPresent(Information -> findedCompany.setInformation(company.getInformation()));

        return companyRepository.save(findedCompany);
    }

    public  void deleteCompany(long companyId) {
        Company findedCompany = findVerifiedCompany(companyId);
        companyRepository.delete(findedCompany);
    }
}

