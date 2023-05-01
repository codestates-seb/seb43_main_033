package main.main.laborcontract.repository;

import main.main.laborcontract.entity.LaborContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaborContractRepository extends JpaRepository<LaborContract, Long> {
}
