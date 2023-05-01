package main.main.userbank.repository;

import main.main.userbank.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankRepository extends JpaRepository<UserBank, Long> {

}
