package main.main.memberbank.service;

import lombok.RequiredArgsConstructor;
import main.main.bank.entity.Bank;
import main.main.bank.service.BankService;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.memberbank.entity.MemberBank;
import main.main.memberbank.repository.MemberBankRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberBankService {

    private final MemberBankRepository memberBankRepository;
    private final MemberService memberService;
    private final BankService bankService;
    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyService companyService;


    public MemberBank createMemberBank(MemberBank memberBank, long authenticationMemberId) {
        checkIdentity(authenticationMemberId);
        verifyExistAccountNumber(memberBank.getAccountNumber());
        createMainAccount(memberBank);

        return memberBankRepository.save(memberBank);
    }


    public MemberBank findMemberBank(long memberBankId) {
        return findVerifiedMemberBank(memberBankId);
    }


    public List<MemberBank> findMemberBanksByMemberId(long memberId) {
        return memberBankRepository.findByMemberMemberId(memberId);
    }

    public Page<MemberBank> findMemberBanks(int page, int size) {
        return memberBankRepository.findAll(PageRequest.of(page, size, Sort.by("memberBankId").ascending()));
    }

    private MemberBank findVerifiedMemberBank(long memberBankId) {
        Optional<MemberBank> optionalMemberBank = memberBankRepository.findById(memberBankId);
        MemberBank findedMemberBank = optionalMemberBank.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBERBANK_NOT_FOUND));
        return findedMemberBank;
    }


    public MemberBank updateMemberBank(MemberBank memberBank, long authenticationMemberId) {

        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());
        Member member = findedMemberBank.getMember();
        Company company = null;
        if (member.getCompany() != null) {
            company = companyService.findCompany(member.getCompany().getCompanyId());
        }

        checkPermission(authenticationMemberId, company);

        if (!findedMemberBank.getAccountNumber().equals(memberBank.getAccountNumber())) {
            verifyExistAccountNumber(memberBank.getAccountNumber());
            findedMemberBank.setAccountNumber(memberBank.getAccountNumber());
        }

        updateMainAccount(memberBank);
        updateBank(memberBank);

        return memberBankRepository.save(findedMemberBank);
    }


    private void verifyExistAccountNumber(String accountNumber) {
        Optional<MemberBank> findedByAccountNumber = memberBankRepository.findByAccountNumber(accountNumber);
        if (findedByAccountNumber.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBERBANK_ACCOUNTNUM_EXISTS);
        }
    }

    private void updateAccountNumber(MemberBank memberBank) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());

        Optional.ofNullable(memberBank.getAccountNumber())
                .ifPresent(accountNumber -> findedMemberBank.setAccountNumber(accountNumber));
    }

    private void updateBank(MemberBank memberBank) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());

        if (memberBank.getBank() != null && memberBank.getBank().getBankId() != null) {
            Long newBankId = memberBank.getBank().getBankId();
            Long currentBankId = findedMemberBank.getBank().getBankId();

            if (!newBankId.equals(currentBankId)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_BANK_UPDATE, "은행을 변경하려면 계좌 삭제 후 다시 진행해 주세요.");
            }
        }
    }

    private void createMainAccount(MemberBank memberBank) {
        Member member = memberService.findMember(memberBank.getMember().getMemberId());
        Bank bank = bankService.findBank(memberBank.getBank().getBankId());
        List<MemberBank> memberBanks = member.getMemberBanks();

        boolean hasMainAccount = memberBanks.stream()
                .anyMatch(MemberBank::isMainAccount);

        if (hasMainAccount) {
            if (memberBank.isMainAccount()) {
                memberBanks.forEach(mb -> mb.setMainAccount(false));
            } else {
                memberBank.setMainAccount(false);
            }
        } else {
            if (memberBanks.isEmpty() && !memberBank.isMainAccount()) {
                throw new BusinessLogicException(ExceptionCode.MEMBERBANK_ONLY_ONE, "적어도 하나의 계좌를 주계좌로 지정해야합니다.");
            }

        }

        memberBank.setMember(member);
        memberBank.setBank(bank);
        memberBanks.add(memberBank);

    }

    private void updateMainAccount(MemberBank memberBank) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBank.getMemberBankId());
        Member member = memberService.findMember(memberBank.getMember().getMemberId());
        Bank bank = bankService.findBank(memberBank.getBank().getBankId());
        List<MemberBank> memberBanks = member.getMemberBanks();

        boolean hasMainAccount = memberBanks.stream()
                .filter(mb -> mb != findedMemberBank)
                .anyMatch(MemberBank::isMainAccount);

        if (memberBanks.size() == 1 && !memberBank.isMainAccount()) {
            throw new BusinessLogicException(ExceptionCode.MEMBERBANK_ONLY_ONE, "적어도 하나의 계좌를 주계좌로 지정해야합니다.");
        }

        if (memberBanks.size() > 1 && !memberBank.isMainAccount() && !hasMainAccount) {
            throw new BusinessLogicException(ExceptionCode.MEMBERBANK_ONLY_ONE, "적어도 하나의 계좌를 주계좌로 지정해야합니다.");
        }

        memberBanks.forEach(mb -> mb.setMainAccount(false));
        findedMemberBank.setMainAccount(true);

        findedMemberBank.setMember(member);
        findedMemberBank.setBank(bank);


    }

    public void deleteMemberBank(long memberBankId, long authenticationMemberId) {
        MemberBank findedMemberBank = findVerifiedMemberBank(memberBankId);
        Member member = findedMemberBank.getMember();
        Company company = null;
        if (member.getCompany() != null) {
            company = companyService.findCompany(member.getCompany().getCompanyId());
        }

        checkPermission(authenticationMemberId, company);
        List<MemberBank> memberBanks = member.getMemberBanks();

        if (memberBanks.size() == 1) {
            memberBankRepository.delete(findedMemberBank);

        } else if (memberBanks.size() >= 2) {
            memberBankRepository.delete(findedMemberBank);

            boolean foundNewMainAccount = false;
            MemberBank lastMemberBank = null;

            for (MemberBank memberBank : memberBanks) {
                if (!memberBank.equals(findedMemberBank)) {
                    lastMemberBank = memberBank;
                    memberBank.setMainAccount(false);
                    memberBankRepository.save(memberBank);
                }
            }

            if (lastMemberBank != null) {
                lastMemberBank.setMainAccount(true);
                memberBankRepository.save(lastMemberBank);
            }
        }
    }

    private void checkPermission(long authenticationMemberId, Company company) { // 본인이거나 매니저일경우 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        Member member = memberService.findMember(authenticationMemberId);

        if (company != null) {
            CompanyMember companyAndMember = companyMemberRepository.findByMemberAndCompany(member, company);
            if (!isAuthorizedMember(member, authenticationMemberId) && !isManager(companyAndMember)) {
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
            }
        } else {
            if (!isAuthorizedMember(member, authenticationMemberId)) {
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
            }
        }
    }

    private void checkIdentity(long authenticationMemberId) { // 본인일때만 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        Member member = memberService.findMember(authenticationMemberId);
        if (!isAuthorizedMember(member, authenticationMemberId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void checkManager(long authenticationMemberId, Company company) { // 매니저일때만 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyAndMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (!isManager(companyAndMember)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }


    private boolean isAuthorizedMember(Member member, long authenticationMemberId) {
        return member != null && member.getMemberId().equals(authenticationMemberId);
    }

    private boolean isManager(CompanyMember companyMember) {
        return companyMember != null && companyMember.getRoles().contains("MANAGER");
    }

}









//    public void setMainAccount(Member member, Long memberBankId) {
//        List<MemberBank> memberBanks = member.getMemberBanks();
//
//        Map<MemberBank, Integer> countMap = new HashMap<>();
//        for (MemberBank memberBank : memberBanks) {
//            Integer count = salaryStatementRepository.countByMemberBankMemberBankId(memberBank.getMemberBankId());
//            countMap.put(memberBank, count);
//        }
//
//        MemberBank mainMemberBank = Collections.max(countMap.entrySet(), Map.Entry.comparingByValue()).getKey();
//
//        if (countMap.containsValue(countMap.get(mainMemberBank))) {
//            mainMemberBank = memberBanks.stream()
//                    .max(Comparator.comparing(MemberBank::getMemberBankId))
//                    .orElse(mainMemberBank);
//        }
//
//        for (MemberBank memberBank : memberBanks) {
//            memberBank.setMainAccount(false);
//        }
//
//        switch (memberBanks.size()) {
//            case 1:
//                memberBanks.get(0).setMainAccount(true);
//                break;
//            default:
//                if (countMap.values().stream().distinct().count() == 1) {
//                    MemberBank highestIdMemberBank = memberBanks.stream()
//                            .max(Comparator.comparing(MemberBank::getMemberBankId))
//                            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND));
//                    highestIdMemberBank.setMainAccount(true);
//                } else {
//                    mainMemberBank.setMainAccount(true);
//                }
//                break;
//        }
//    }
