package main.main.company.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.repository.CompanyRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    private Company findVerifiedCompany(long companyId) {
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

    public void deleteCompany(long companyId) {
        Company findedCompany = findVerifiedCompany(companyId);
        companyRepository.delete(findedCompany);
    }

    public Boolean uploading(MultipartFile file, long companyId, String url) {

        Boolean result = Boolean.TRUE;

        String dir = Long.toString(companyId);
        String extension = getExtension(file);

        String newFileName = dir + extension;


        try {
            File folder = new File("img" + File.separator + "회사_대표_이미지" + File.separator + dir);
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

}

