package main.main.laborcontract.controller;

import lombok.RequiredArgsConstructor;
import main.main.laborcontract.dto.LaborContractDto;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/laborcontracts")
@RequiredArgsConstructor
public class LaborContractController {
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;

    @PostMapping
    public ResponseEntity postLaborContract(@RequestBody LaborContractDto.Post requestBody) {
        laborContractService.creatLaborContract(laborContractMapper.postToLaborContract(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("{laborcontract-id}")
    public ResponseEntity patchLaborContract(@PathVariable("laborcontract-id") long laborContractId,
                                             @RequestBody LaborContractDto.Patch requestBody) {
        laborContractService.updateLaborContract(laborContractId, laborContractMapper.patchToLaborContract(requestBody));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{laborcontract-id}")
    public ResponseEntity getLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        LaborContract laborContract = laborContractService.findLaborContract(laborContractId);

        return new ResponseEntity<>(laborContractMapper.laborContractToResponse(laborContract), HttpStatus.OK);
    }

    @DeleteMapping("/{laborcontract-id}")
    public ResponseEntity deleteLaborContract(@PathVariable("laborcontract-id") long laborContractId) {
        laborContractService.deleteLaborContract(laborContractId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
