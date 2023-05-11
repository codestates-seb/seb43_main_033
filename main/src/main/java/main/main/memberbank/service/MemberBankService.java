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
import main.main.salarystatement.repository.SalaryStatementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberBankService {

    private final MemberBankRepository memberBankRepository;
    private final SalaryStatementRepository salaryStatementRepository;

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


    public Page<MemberBank> findMemberBanks(int page, int size) {
        return memberBankRepository.findAll(PageRequest.of(page, size, Sort.by("memberBankId").ascending()));
    }

    private MemberBank findVerifiedMemberBank(long memberBankId) {
        Optional<MemberBank> optionalMemberBank = memberBankRepository.findById(memberBankId);
        MemberBank findedMemberBank = optionalMemberBank.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBERBANK_NOT_FOUND));
        return findedMemberBank;
    }

    public MemberBank updateMemberBank(MemberBank memberBank) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());

        Optional.ofNullable(memberBank.getAccountNumber())
                .ifPresent(accountNumber -> findedMemberBank.setAccountNumber(accountNumber));

        if (memberBank.getBank() != null && memberBank.getBank().getBankId() != null) {
            Long newBankId = memberBank.getBank().getBankId();
            Long currentBankId = findedMemberBank.getBank().getBankId();

            if (!newBankId.equals(currentBankId)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_BANK_UPDATE);
            }
        }

        return memberBankRepository.save(findedMemberBank);
    }





    public void deleteMemberBank(long memberBankId) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBankId);
        memberBankRepository.delete(findedMemberBank);
    }



    public void setMainAccount(Member member, Long memberBankId) {
        List<MemberBank> memberBanks = member.getMemberBanks();

        Map<MemberBank, Integer> countMap = new HashMap<>();
        for (MemberBank memberBank : memberBanks) {
            Integer count = salaryStatementRepository.countByMemberBankMemberBankId(memberBank.getMemberBankId());
            countMap.put(memberBank, count);
        }

        MemberBank mainMemberBank = Collections.max(countMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        if (countMap.containsValue(countMap.get(mainMemberBank))) {
            mainMemberBank = memberBanks.stream()
                    .max(Comparator.comparing(MemberBank::getMemberBankId))
                    .orElse(mainMemberBank);
        }

        for (MemberBank memberBank : memberBanks) {
            memberBank.setMainAccount(false);
        }

        switch (memberBanks.size()) {
            case 1:
                memberBanks.get(0).setMainAccount(true);
                break;
            default:
                if (countMap.values().stream().distinct().count() == 1) {
                    MemberBank highestIdMemberBank = memberBanks.stream()
                            .max(Comparator.comparing(MemberBank::getMemberBankId))
                            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND));
                    highestIdMemberBank.setMainAccount(true);
                } else {
                    mainMemberBank.setMainAccount(true);
                }
                break;
        }
    }

}
