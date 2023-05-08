package main.main.membercompany.service;

import lombok.RequiredArgsConstructor;
import main.main.membercompany.repository.MemberCompanyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCompanyService {
    private final MemberCompanyRepository memberCompanyRepository;
}
