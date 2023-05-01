package main.main.user.repository;

import main.main.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByResidentNumber(String residentNumber);
}
