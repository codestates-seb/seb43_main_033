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
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.entity.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompanyMemberService {

    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyService companyService;
    private final MemberService memberService;
    private final CustomAuthorityUtils authorityUtils;
    private final CompanyMemberMapper companyMemberMapper;

    public CompanyMember createCompanyMember(CompanyMember companyMember, long authenticationMemberId) {

        Company company = companyService.findCompany(companyMember.getCompany().getCompanyId());
        Member member = memberService.findMember(companyMember.getMember().getMemberId());

        if (companyMemberRepository.existsByCompanyAndMember(company, member)) {
            throw new BusinessLogicException(ExceptionCode.COMPANYMEMBER_EXISTS);
        }

        checkPermission(authenticationMemberId, company);

        List<String> roles = new ArrayList<>();
        roles.add("MEMBER");

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

    public void deleteCompanyMember(long companyMemberId, long authenticationMemberId) {

        CompanyMember findedCompanyMember = findVerifiedCompanyMember(companyMemberId);
        Company company = companyService.findCompany(findedCompanyMember.getCompany().getCompanyId());
        checkPermission(authenticationMemberId, company);
        companyMemberRepository.delete(findedCompanyMember);
    }


    public CompanyMember updateCompanyMember(CompanyMember companyMember, long authenticationMemberId) {

        CompanyMember findedCompanyMember = findVerifiedCompanyMember(companyMember.getCompanyMemberId());

        checkIdentity(authenticationMemberId);
        Optional.ofNullable(companyMember.getGrade())
                .ifPresent(grade -> findedCompanyMember.setGrade(grade));
        Optional.ofNullable(companyMember.getTeam())
                .ifPresent(team -> findedCompanyMember.setTeam(team));

        return companyMemberRepository.save(findedCompanyMember);
    }

    public CompanyMember companyMemberUpdate(long companyMemberId, String status, long authenticationMemberId) {
        CompanyMember companyMember = findCompanyMember(companyMemberId);
        Company company = companyService.findCompany(companyMember.getCompany().getCompanyId());
        checkManager(authenticationMemberId, company);

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

        List<String> roles = new ArrayList<>(requestBody.getRoles());

        if (roles.contains("ADMIN")) {
            roles.addAll(Arrays.asList("MANAGER", "MEMBER"));
        } else if (roles.contains("MANAGER")) {
            roles.add("MEMBER");
        } else if (roles.contains("MEMBER")) {
            roles.add("MEMBER");
        }

        companyMember.setRoles(roles);

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

    public Page<CompanyMember> findCompanyMembersByCompanyId(int page, long companyId, boolean filter) {
        if(filter) {
            LocalDateTime now = LocalDateTime.now();

            return companyMemberRepository.findAllByStatusOfWorksNoteNotAndStatusOfWorksStartTimeBeforeAndStatusOfWorksFinishTimeAfter(StatusOfWork.Note.정상근무, now, now, PageRequest.of(page, 10, Sort.by("companyMemberId").ascending()));
        }
        return companyMemberRepository.findAllByCompanyCompanyId(companyId, PageRequest.of(page, 10, Sort.by("companyMemberId").ascending()));
    }

    public List<CompanyMember> findCompanyMembersByMemberId(long memberId) {
        return companyMemberRepository.findAllByMemberMemberId(memberId);
    }

    private void checkPermission(long authenticationMemberId, Company company) { // 본인이거나 매니저일경우 패스
        if (authenticationMemberId == -1) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        Member member = memberService.findMember(authenticationMemberId);
        CompanyMember companyAndMember = companyMemberRepository.findByMemberAndCompany(member, company);
        if (!isAuthorizedMember(member, authenticationMemberId) && !isManager(companyAndMember)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
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

    private boolean isManager(CompanyMember companyMember) { // 매니저이거나 관리자일때 패스
        return companyMember.getRoles().contains("MANAGER") || companyMember.getRoles().contains("ADMIN") ;
    }

}
