package main.main.statusofwork.repository;

import main.main.statusofwork.entity.StatusOfWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusOfWorkRepository extends JpaRepository<StatusOfWork, Long> {
}
