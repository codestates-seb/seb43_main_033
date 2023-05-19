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
import main.main.utils.AwsS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final CompanyService companyService;
    private final MemberService memberService;
    private final AwsS3Service awsS3Service;
    private final CompanyRepository companyRepository;
    private final CompanyMemberRepository companyMemberRepository;

    public void companyImageUpLoad(MultipartFile file, long companyId, long authenticationMemberId) {

        Company company = companyService.findCompany(companyId);

        checkPermission(authenticationMemberId, company);
        if (company.getCompanyImageFile() != null) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_ALREADY_EXISTS);
        }
        if (file != null) {
            String[] uriList = awsS3Service.uploadFile(file);
            company.setCompanyImageUri(uriList[0]);
            company.setCompanyImageFile(uriList[1]);
        } else {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }

        companyRepository.save(company);

    }

    public void businessImageUpLoad(MultipartFile file, long companyId, long authenticationMemberId) {

        Company company = companyService.findCompany(companyId);

        checkPermission(authenticationMemberId, company);

        if (company.getCompanyImageFile() != null) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_ALREADY_EXISTS);
        }

        if (file != null) {
            String[] uriList = awsS3Service.uploadFile(file);
            company.setBusinessImageUri(uriList[0]);
            company.setBusinessImageFile(uriList[1]);
        } else {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }

        companyRepository.save(company);

    }

    public void postImage(String type, MultipartFile file, long companyId, long authenticationMemberId) {
        if (type.equals("company_image")) {
            companyImageUpLoad(file, companyId, authenticationMemberId);
        } else if (type.equals("business_image")) {
            businessImageUpLoad(file, companyId, authenticationMemberId);
        }

    }

    public void patchImage(String type, MultipartFile file, long companyId, long authenticationMemberId) {
        Company company = companyService.findCompany(companyId);

        checkPermission(authenticationMemberId, company);

        if (type.equals("company_image")) {
            awsS3Service.deleteFile(company.getCompanyImageFile());
            company.setCompanyImageUri(null);
            company.setCompanyImageFile(null);
            String[] uriList = awsS3Service.uploadFile(file);
            company.setCompanyImageUri(uriList[0]);
            company.setCompanyImageFile(uriList[1]);
            companyRepository.save(company);

        } else if (type.equals("business_image")) {
            awsS3Service.deleteFile(company.getBusinessImageFile());
            company.setBusinessImageUri(null);
            company.setBusinessImageFile(null);
            String[] uriList = awsS3Service.uploadFile(file);
            company.setBusinessImageUri(uriList[0]);
            company.setBusinessImageFile(uriList[1]);
            companyRepository.save(company);
        }
    }

    public String getImage(String type, long companyId) {
        Company company = companyService.findCompany(companyId);
        String uri = null;
        if (company.getCompanyImageFile() == null) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }

        if (type.equals("company_image")) {
            uri = company.getCompanyImageUri();
        } else if (type.equals("business_image")) {
            uri = company.getBusinessImageUri();
        }
        return uri;
    }

    public void deleteImage(String type, long companyId, long authenticationMemberId) {
        Company company = companyService.findCompany(companyId);
        checkPermission(authenticationMemberId, company);

        String fileName = null;

        if (company.getCompanyImageFile() == null) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }

        if (type.equals("company_image")) {
            fileName = company.getCompanyImageFile();
            company.setCompanyImageUri(null);
            company.setCompanyImageFile(null);
        } else if (type.equals("business_image")) {
            fileName = company.getBusinessImageFile();
            company.setBusinessImageUri(null);
            company.setBusinessImageFile(null);
        }

        if (fileName != null) {
            awsS3Service.deleteFile(fileName);
            companyRepository.save(company);
        }
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
}
