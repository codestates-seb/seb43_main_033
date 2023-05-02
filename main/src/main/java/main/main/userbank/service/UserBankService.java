package main.main.userbank.service;

import lombok.RequiredArgsConstructor;
import main.main.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBankService {

    private final MemberRepository memberRepository;
}
