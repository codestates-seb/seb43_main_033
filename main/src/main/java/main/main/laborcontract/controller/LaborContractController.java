package main.main.laborcontract.controller;

import lombok.RequiredArgsConstructor;
import main.main.laborcontract.mapper.LaborContractMapper;
import main.main.laborcontract.service.LaborContractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/laborcontract")
@RequiredArgsConstructor
public class LaborContractController {
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;
}
