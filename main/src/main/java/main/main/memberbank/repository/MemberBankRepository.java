package main.main.memberbank.repository;

import main.main.memberbank.entity.MemberBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBankRepository extends JpaRepository<MemberBank, Long> {

}
