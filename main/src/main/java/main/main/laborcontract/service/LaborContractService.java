package main.main.laborcontract.service;

import lombok.RequiredArgsConstructor;
import main.main.laborcontract.repository.LaborContractRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaborContractService {
    private final LaborContractRepository laborContractRepository;
}
