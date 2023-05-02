package main.main.memberbank.service;

import lombok.RequiredArgsConstructor;
import main.main.memberbank.repository.MemberBankRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberBankService {

    private final MemberBankRepository memberBankRepository;
}
