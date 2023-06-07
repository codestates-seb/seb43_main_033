package main.main.laborcontract.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/laborcontracts/v2")
@RequiredArgsConstructor
public class LaborContractControllerV2 {
    private final LaborContractServiceV2 laborContractService;
    private final LaborContractMapper laborContractMapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postLaborContract(@RequestPart LaborContractDto.Post requestPart,
                                            @RequestPart(required = false) MultipartFile file) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        laborContractService.creatLaborContract(laborContractMapper.postToLaborContract(requestPart), file, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/laborcontracts/{laborcontract-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchLaborContract(@PathVariable("laborcontract-id") long laborContractId,
                                             @RequestPart LaborContractDto.Patch requestPart,
                                             @RequestPart(required = false) MultipartFile file) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        LaborContract laborContract = laborContractMapper.patchToLaborContract(requestPart);
        laborContractService.updateLaborContract(laborContractId, laborContract, file, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/laborcontracts/{companymember-id}")
    public ResponseEntity getLaboContractList(@PathVariable("companymember-id") long companyMemberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        List<LaborContract> laborContracts = laborContractService.findLaborContractList(companyMemberId, authenticationMemberId);

        return new ResponseEntity<>(laborContractMapper.laborContractsToResponses(laborContracts), HttpStatus.OK);
    }

    @GetMapping("/laborcontracts/{laborcontract-id}")
    public ResponseEntity getLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        LaborContract laborContract = laborContractService.findLaborContract(laborContractId, authenticationMemberId);

        return new ResponseEntity<>(laborContractMapper.laborContractToResponse(laborContract), HttpStatus.OK);
    }

    @GetMapping("/laborcontracts/worker")
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
}
