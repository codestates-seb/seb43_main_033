package main.main.companymember.service;

import lombok.RequiredArgsConstructor;
import main.main.companymember.repository.CompanyMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMemberService {
    private final CompanyMemberRepository companyMemberRepository;
}
