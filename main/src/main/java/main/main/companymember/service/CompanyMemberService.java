package main.main.companymember.service;

import lombok.RequiredArgsConstructor;
import main.main.auth.utils.CustomAuthorityUtils;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.mapper.CompanyMemberMapper;
import main.main.companymember.repository.CompanyMemberRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.statusofwork.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyMemberService {

    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyService companyService;
    private final MemberService memberService;
    private final CustomAuthorityUtils authorityUtils;
    private final CompanyMemberMapper companyMemberMapper;

    public CompanyMember createCompanyMember(CompanyMember companyMember) {

        Company company = companyService.findCompany(companyMember.getCompany().getCompanyId());
        Member member = memberService.findMember(companyMember.getMember().getMemberId());

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        companyMember.setRoles(roles);

        companyMember.setVacation(new Vacation());
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

    public CompanyMember companyMemberUpdate(long companyMemberId, String status) {
        CompanyMember companyMember = findCompanyMember(companyMemberId);
        if (status.equals("pending")) {
            companyMember.setStatus(Status.PENDING);
        } else if (status.equals("approved")) {
            companyMember.setStatus(Status.APPROVED);
        } else if (status.equals("refuse")) {
            companyMember.setStatus(Status.REFUSE);
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_STATUS);
        }

        return companyMemberRepository.save(companyMember);
    }


    public CompanyMember updateCompanyMemberRole(Long companyMemberId, CompanyMemberDto.Patch requestBody) {

        CompanyMember companyMember = companyMemberRepository.findById(companyMemberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMPANYMEMBER_NOT_FOUND));

        companyMember.setRoles(requestBody.getRoles());
        return companyMemberRepository.save(companyMember);
    }

    public Page<CompanyMember> findCompanyMembersByCompanyId(int page, String sortBy, Long companyId) {
        Status status;
        if (sortBy.equalsIgnoreCase("pending")) {
            status = Status.PENDING;
        } else if (sortBy.equalsIgnoreCase("approved")) {
            status = Status.APPROVED;
        } else if (sortBy.equalsIgnoreCase("refuse")) {
            status = Status.REFUSE;
        } else {
            status = null;
        }
        if (status != null) {
            return companyMemberRepository.findAllByCompanyCompanyIdAndStatus(companyId, status, PageRequest.of(page, 10, Sort.by("status").descending()));
        } else {
            return companyMemberRepository.findAllByCompanyCompanyId(companyId, PageRequest.of(page, 10, Sort.by("status").descending()));
        }
    }

}
