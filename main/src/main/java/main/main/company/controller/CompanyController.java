package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.company.dto.CompanyDto;
import main.main.company.entity.Company;
import main.main.company.mapper.CompanyMapper;
import main.main.company.service.CompanyService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@RequestMapping("/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final static String COMPANY_DEFAULT_URL = "/companies";
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PostMapping
    public ResponseEntity postCompany(@Valid @RequestBody CompanyDto.Post requestBody) {
        Company company = companyService.createCompany(companyMapper.companyPostToCompany(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{company-id}")
    public ResponseEntity patchCompany(@Positive @PathVariable("company-id") long companyId,
                                       @Valid @RequestBody CompanyDto.Patch requestBody) {

        requestBody.setCompanyId(companyId);
        companyService.updateCompany(companyMapper.companyPatchToCompany(requestBody));

        return new ResponseEntity<>(companyMapper
                        .companyToCompanyResponse(companyService.findCompany(companyId)), HttpStatus.OK);
    }

    @GetMapping("/{company-id}")
    public ResponseEntity getCompany(@PathVariable("company-id") @Positive long companyId) {
        Company company = companyService.findCompany(companyId);
        return new ResponseEntity<>(companyMapper.companyToCompanyResponse(company), HttpStatus.OK);

    }

//    @GetMapping
//    public ResponseEntity getCompanies(@Positive @RequestParam int page, @RequestParam int size) {
//        Page<Company> pageCompanies = companyService.findCompanies(page - 1, size);
//        List<Company> companies = pageCompanies.getContent();
//
//        return new ResponseEntity<>(new ListPageResponseDto<>(companies, pageCompanies), HttpStatus.OK);
//    }

    @GetMapping("/salary/{company-id}")
    public ResponseEntity totalSalaryOfCompany(@PathVariable("company-id") @Positive long companyId) {
        Company company = companyService.findCompany(companyId);
        BigDecimal totalSalaryOfCompany = companyService.calculateTotalSalaryOfCompany(companyId);
        return new ResponseEntity<>(companyMapper.companyToCompanyResponseForSalary(company, totalSalaryOfCompany),HttpStatus.OK);
    }

    @DeleteMapping("/{company-id}")
    public ResponseEntity deleteCompany(@Positive @PathVariable("company-id") long companyId) {
        companyService.deleteCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping(path = "/imageupload/{company-id}")
    public ResponseEntity postCompanyImageUpload(@PathVariable("company-id") Long companyId,
                                          @RequestPart(required = false) MultipartFile file){

        String dir = Long.toString(companyId);
        String extension = companyService.getExtension(file);

        if (!extension.equalsIgnoreCase(".png") && !extension.equalsIgnoreCase(".jpeg")) {

            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_SUPPORT);
        }

        companyService.uploading(file, companyId, dir);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/image/{company-id}")
    public ResponseEntity<byte[]> getCompanyProfileImage(@PathVariable("company-id") long companyId) throws IOException {
        String dir = Long.toString(companyId);
        String filePath = "img/회사 대표 이미지/" + dir + "/" + dir + ".jpeg";
        File file = new File(filePath);
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (Exception e) {
            filePath = "img/회사 대표 이미지/" + dir + "/" + dir + ".png";
            file = new File(filePath);
            fileName = file.getName();
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            inputStream = new FileInputStream(filePath);
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

}