package main.main.laborcontract.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LaborContractController {
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;

    @PostMapping(path = "/manager/{company-id}/members/{companymember-id}/laborcontracts",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postLaborContract(@PathVariable("company-id") long companyId,
                                            @PathVariable("companymember-id") long companyMemberId,
                                            @RequestPart LaborContractDto.Post requestPart,
                                            @RequestPart(required = false) MultipartFile file) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        requestPart.setCompanyId(companyId);
        requestPart.setCompanyMemberId(companyMemberId);
        laborContractService.creatLaborContract(laborContractMapper.postToLaborContract(requestPart), file, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/manager/{company-id}/laborcontracts/{laborcontract-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchLaborContract(@PathVariable("company-id") long companyId,
                                             @PathVariable("laborcontract-id") long laborContractId,
                                             @RequestPart LaborContractDto.Patch requestPart,
                                             @RequestPart(required = false) MultipartFile file) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        LaborContract laborContract = laborContractMapper.patchToLaborContract(requestPart);
        laborContract.getCompany().setCompanyId(companyId);
        laborContractService.updateLaborContract(laborContractId, laborContract, file, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/manager/{company-id}/laborcontracts/{laborcontract-id}")
    public ResponseEntity getLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        LaborContract laborContract = laborContractService.findLaborContract(laborContractId, authenticationMemberId);

        return new ResponseEntity<>(laborContractMapper.laborContractToResponse(laborContract), HttpStatus.OK);
    }

    @GetMapping("/worker/mycontract")
    public ResponseEntity getLaborContract() {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        List<LaborContract> laborContracts = laborContractService.findLaborContract(authenticationMemberId)
                .stream()
                .sorted(Comparator.comparing(LaborContract::getId).reversed())
                .collect(Collectors.toList());

        return new ResponseEntity<>(laborContractMapper.laborContractsToResponses(laborContracts), HttpStatus.OK);
    }

    @DeleteMapping("/laborcontracts/{laborcontract-id}")
    public ResponseEntity deleteLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        laborContractService.deleteLaborContract(laborContractId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static HttpHeaders getContentType(String imageType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (imageType.equals("png")) {
            httpHeaders.setContentType(MediaType.IMAGE_PNG);
        } else if (imageType.equals("pdf")) {
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        } else if (imageType.equals("jpeg") || imageType.equals("jpg")) {
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        }
        return httpHeaders;
    }
}
