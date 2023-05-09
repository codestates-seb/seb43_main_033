package main.main.companymember.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyMemberService {

    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyService companyService;
    private final MemberService memberService;

    public CompanyMember createCompanyMember(CompanyMember companyMember) {

        Company company = companyService.findCompany(companyMember.getCompany().getCompanyId());
        Member member = memberService.findMember(companyMember.getMember().getMemberId());

        companyMember.setCompany(company);
        companyMember.setMember(member);

        return companyMemberRepository.save(companyMember);
    }

    public CompanyMember findCompanyMember(long companyMemberId) {
        return findVerifiedCompanyMember(companyMemberId);
    }

    public CompanyMember findVerifiedCompanyMember(long companyMemberId) {
        Optional<CompanyMember> optionalCompanyMember = companyMemberRepository.findById(companyMemberId);
        CompanyMember findedCompanyMember = optionalCompanyMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findedCompanyMember;
    }

    public Page<CompanyMember> findCompanyMembers(int page, int size) {
        return companyMemberRepository.findAll(PageRequest.of(page, size, Sort.by("companyMemberId").descending()));
    }

    public void deleteCompanyMember(long companyMemberId) {
        CompanyMember findedCompanyMember = findVerifiedCompanyMember(companyMemberId);
        companyMemberRepository.delete(findedCompanyMember);
    }


    public CompanyMember updateCompanyMember(CompanyMember companyMember) {
        CompanyMember findedCompanyMember = findVerifiedCompanyMember(companyMember.getCompanyMemberId());

        Optional.ofNullable(companyMember.getGrade())
                .ifPresent(grade -> findedCompanyMember.setGrade(grade));
        Optional.ofNullable(companyMember.getTeam())
                .ifPresent(team -> findedCompanyMember.setTeam(team));

        return companyMemberRepository.save(findedCompanyMember);
    }

}