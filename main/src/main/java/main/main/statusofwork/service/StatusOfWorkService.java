package main.main.statusofwork.service;

import lombok.RequiredArgsConstructor;
import main.main.statusofwork.repository.StatusOfWorkRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusOfWorkService {
    private final StatusOfWorkRepository statusOfWorkRepository;
}
