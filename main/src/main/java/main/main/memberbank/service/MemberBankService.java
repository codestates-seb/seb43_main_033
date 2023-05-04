package main.main.memberbank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.entity.Bank;
import main.main.bank.service.BankService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.memberbank.entity.MemberBank;
import main.main.memberbank.repository.MemberBankRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberBankService {

    private final MemberBankRepository memberBankRepository;
    private final MemberService memberService;
    private final BankService bankService;


    public MemberBank createMemberBank(MemberBank memberBank) {

        Member member = memberService.findMember(memberBank.getMember().getMemberId());
        Bank bank = bankService.findBank(memberBank.getBank().getBankId());

        memberBank.setMember(member);
        memberBank.setBank(bank);
        return memberBankRepository.save(memberBank);
    }


    public MemberBank findMemberBank(long memberBankId) {
        return findVerifiedMemberBank(memberBankId);
    }

    private MemberBank findVerifiedMemberBank(long memberBankId) {
        Optional<MemberBank> optionalMemberBank = memberBankRepository.findById(memberBankId);
        MemberBank findedMemberBank = optionalMemberBank.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBERBANK_NOT_FOUND));
        return findedMemberBank;
    }

    public MemberBank updateMemberBank(MemberBank memberBank) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());

        Optional.ofNullable(memberBank.getAccountNumber())
                .ifPresent(AccountNumber -> findedMemberBank.setAccountNumber(memberBank.getAccountNumber()));

        return memberBankRepository.save(findedMemberBank);
    }

    public void deleteMemberBank(long memberBankId) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBankId);
        memberBankRepository.delete(findedMemberBank);
    }

}
