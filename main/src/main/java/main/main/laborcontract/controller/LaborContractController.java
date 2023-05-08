package main.main.laborcontract.controller;

import lombok.RequiredArgsConstructor;
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

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/laborcontracts")
@RequiredArgsConstructor
public class LaborContractController {
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postLaborContract(@RequestPart LaborContractDto.Post requestPart,
                                            @RequestPart(required = false) MultipartFile file) {
        laborContractService.creatLaborContract(laborContractMapper.postToLaborContract(requestPart), file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{laborcontract-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchLaborContract(@PathVariable("laborcontract-id") long laborContractId,
                                             @RequestPart LaborContractDto.Patch requestPart,
                                             @RequestPart(required = false) MultipartFile file) {
        laborContractService.updateLaborContract(laborContractId, laborContractMapper.patchToLaborContract(requestPart), file);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{laborcontract-id}")
    public ResponseEntity getLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        LaborContract laborContract = laborContractService.findLaborContract(laborContractId);

        return new ResponseEntity<>(laborContractMapper.laborContractToResponse(laborContract), HttpStatus.OK);
    }

    @GetMapping("/{laborcontract-id}/file")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("laborcontract-id") long laborContractId) throws IOException {
        HashMap<byte[], String> image = laborContractService.getImage(laborContractId);
        byte[] imageByteArray = image.keySet().iterator().next();
        String imageType = image.get(imageByteArray);

        HttpHeaders httpHeaders = getContentType(imageType);
        return new ResponseEntity<>(imageByteArray, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{laborcontract-id}")
    public ResponseEntity deleteLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        laborContractService.deleteLaborContract(laborContractId);

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
