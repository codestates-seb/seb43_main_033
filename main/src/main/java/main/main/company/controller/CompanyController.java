package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.dto.ListPageResponseDto;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequestMapping("/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final static String COMPANY_DEFAULT_URL = "/companies";
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PostMapping
    public ResponseEntity postCompany(@Valid @RequestBody CompanyDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        companyService.createCompany(companyMapper.companyPostToCompany(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{company-id}")
    public ResponseEntity patchCompany(@Positive @PathVariable("company-id") long companyId,
                                       @Valid @RequestBody CompanyDto.Patch requestBody) {

        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        requestBody.setCompanyId(companyId);

        companyService.updateCompany(companyMapper.companyPatchToCompany(requestBody), requestBody.getBusinessNumber(), authenticationMemberId);
//        BigDecimal[] SalaryOfCompany = companyService.salaryOfCompany(companyId);
//
//        Company company = companyService.findCompany(companyId);
//        List<CompanyMember> companyMembers = company.getCompanyMembers();
//        return new ResponseEntity<>(companyMapper
//                .companyToCompanyResponse(company, SalaryOfCompany, companyMembers), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{company-id}")
    public ResponseEntity getCompany(@PathVariable("company-id") @Positive long companyId) {
        Company company = companyService.findCompany(companyId);
        BigDecimal[] SalaryOfCompany = companyService.salaryOfCompany(companyId);
        List<CompanyMember> companyMembers = company.getCompanyMembers();

        return new ResponseEntity<>(companyMapper.companyToCompanyResponse(company, SalaryOfCompany, companyMembers), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity getCompanies(@Positive @RequestParam int page, @RequestParam int size) {
        Page<Company> pageCompanies = companyService.findCompanies(page - 1, size);
        List<Company> companies = pageCompanies.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(companyMapper.companiesToCompaniesResponse(companies), pageCompanies), HttpStatus.OK);
    }

    @DeleteMapping("/{company-id}")
    public ResponseEntity deleteCompany(@Positive @PathVariable("company-id") long companyId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        companyService.deleteCompany(companyId, authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    @GetMapping("/businessnumber/{member-id}")
    public ResponseEntity<List<Map<String, Object>>> getBusinessNumberByMember(@PathVariable("member-id") @Positive long memberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        List<Map<String, Object>> business = companyService.findBusinessNumbersByRole(memberId, authenticationMemberId);
        return new ResponseEntity<>(business, HttpStatus.OK);
    }





    @PostMapping(path = "/{type}/{company-id}")
    public ResponseEntity<Void> postImageUpload(
            @PathVariable("type") String type,
            @PathVariable("company-id") Long companyId,
            @RequestPart(required = false) MultipartFile file) {

        String dir = Long.toString(companyId);
        String extension = companyService.getExtension(file);
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();


        if (!extension.equalsIgnoreCase(".png") && !extension.equalsIgnoreCase(".jpeg")) {

            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_SUPPORT);
        }

        if (type.equals("companyimage")) {
            companyService.comapanyuploading(file, companyId, dir, authenticationMemberId);
        } else if (type.equals("businessimage")) {
            companyService.businessuploading(file, companyId, dir, authenticationMemberId);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{type}/{company-id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("type") String imageType,
                                           @PathVariable("company-id") long companyId) throws IOException {
        String dir = Long.toString(companyId);
        String filePath = "img" + "/" + "회사_이미지" + "/" + dir + "/";

        if (imageType.equals("companyimage")) {
            filePath += "회사_대표_이미지" + "/" + dir;
        } else if (imageType.equals("businessimage")) {
            filePath += "사업자_등록증_이미지" + "/" + dir;
        }

        File file = new File(filePath + ".jpeg");
        if (!file.exists()) {
            file = new File(filePath + ".png");
        }

        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            throw new FileNotFoundException("사진을 찾을 수 없습니다.");
        } finally {
            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            HttpHeaders httpHeaders = new HttpHeaders();
            if ("png".equals(fileExtension)) {
                httpHeaders.setContentType(MediaType.IMAGE_PNG);
            } else if ("jpeg".equals(fileExtension)) {
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            }

            return new ResponseEntity<>(imageByteArray, httpHeaders, HttpStatus.OK);
        }

    }


//    @GetMapping("/salary/{company-id}")
//    public ResponseEntity totalSalaryOfCompany(@PathVariable("company-id") @Positive long companyId) {
//        Company company = companyService.findCompany(companyId);
//        BigDecimal totalSalaryOfCompany = companyService.calculateTotalSalaryOfCompany(companyId);
//        return new ResponseEntity<>(companyMapper.companyToCompanyResponseForSalary(company, totalSalaryOfCompany),HttpStatus.OK);
//    }

//    @PostMapping(path = "/companyimage/{company-id}")
//    public ResponseEntity postCompanyImageUpload(@PathVariable("company-id") Long companyId,
//                                                 @RequestPart(required = false) MultipartFile file) {
//
//        String dir = Long.toString(companyId);
//        String extension = companyService.getExtension(file);
//
//        if (!extension.equalsIgnoreCase(".png") && !extension.equalsIgnoreCase(".jpeg")) {
//
//            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_SUPPORT);
//        }
//
//        companyService.comapanyuploading(file, companyId, dir);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

//
//    @GetMapping("/companyimage/{company-id}")
//    public ResponseEntity<byte[]> getCompanyImage(@PathVariable("company-id") long companyId) throws IOException {
//        String dir = Long.toString(companyId);
//        String filePath = "img" + "/" + "회사_이미지" + "/" + dir + "/" + "회사_대표_이미지" + "/" + dir + ".jpeg";
//        File file = new File(filePath);
//        String fileName = file.getName();
//        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(filePath);
//        } catch (Exception e) {
//            filePath = "img" + "/" + "회사_이미지" + "/" + dir + "/" + "회사_대표_이미지" + "/" + dir + ".png";
//            file = new File(filePath);
//            fileName = file.getName();
//            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
//            inputStream = new FileInputStream(filePath);
//        } finally {
//            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
//            inputStream.close();
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            if ("png".equals(fileExtension)) {
//                httpHeaders.setContentType(MediaType.IMAGE_PNG);
//            } else if ("jpeg".equals(fileExtension)) {
//                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//            }
//            return new ResponseEntity<>(imageByteArray, httpHeaders, HttpStatus.OK);
//        }
//    }
}