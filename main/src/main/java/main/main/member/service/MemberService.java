package main.main.member.service;

import lombok.RequiredArgsConstructor;
import main.main.auth.utils.CustomAuthorityUtils;
import main.main.company.entity.Company;
import main.main.company.repository.CompanyRepository;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.dto.MemberDto;
import main.main.member.dto.Position;
import main.main.member.entity.Member;
import main.main.member.mapper.MemberMapper;
import main.main.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final MemberMapper mapper;
    public Member updateMember(Member member) {
        Member findedMember = findVerifiedMember(member.getMemberId());
        verifiyExitstEmail(member.getEmail());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findedMember.setName(member.getName()));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findedMember.setEmail(member.getEmail()));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findedMember.setPassword(member.getPassword()));
        Optional.ofNullable(member.getPhoneNumber())
                .ifPresent(phoneNumber -> findedMember.setPhoneNumber(member.getPhoneNumber()));
        Optional.ofNullable(member.getResidentNumber())
                .ifPresent(residentNumber -> findedMember.setResidentNumber(member.getResidentNumber()));
        Optional.ofNullable(member.getAddress())
                .ifPresent(address -> findedMember.setAddress(member.getAddress()));
        Optional.ofNullable(member.getPosition())
                .ifPresent(position -> findedMember.setPosition(member.getPosition()));

        return memberRepository.save(findedMember);
    }

//    public Member companyMember (long memberId, long companyId) {
//
//        Member member = findMember(memberId);
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//
//        member.setCompany(company);
//
//        return memberRepository.save(member);
//    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member createMember(Member member) {
        verifiyExitstEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

//        List<String> roles = authorityUtils.createRoles(member.getEmail());
//        member.setRoles(roles);

        return memberRepository.save(member);

    }

    private void verifiyExitstEmail(String email) {
        Optional<Member> findedMemberByEmail = memberRepository.findByEmail(email);
        if (findedMemberByEmail.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_EXISTS);
        }
    }

    private Member findVerifiedMember (Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findedMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findedMember;
    }

    public void deleteMember(Long memberId, long authenticationUserId) {
        checkVerifiedId(authenticationUserId);

        Member findMember = findVerifiedMember(memberId);
        deletePermission(findMember, authenticationUserId);
        memberRepository.delete(findMember);
    }

    private void checkVerifiedId(long authenticationUserId) {
        if (authenticationUserId == -1) throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
    }

    private void deletePermission(Member member, long authenticationUserId) {
        if (!member.getMemberId().equals(authenticationUserId) && !member.getEmail().equals("admin@gmail.com")) {
            throw new BusinessLogicException(ExceptionCode.ONLY_AUTHOR);
        }
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    private void uploadingPermission(Member member, long authenticationMemberId) {
        if (!member.getMemberId().equals(authenticationMemberId) && !member.getEmail().equals("admin@gmail.com")) {
            throw new BusinessLogicException(ExceptionCode.ONLY_AUTHOR);
        }
    }

    public Boolean uploading(MultipartFile file, long memberId, long authenticationMemberId, String url) {

        checkVerifiedId(authenticationMemberId);
        Member findedMember = findVerifiedMember(memberId);
        uploadingPermission(findedMember, authenticationMemberId);

        Boolean result = Boolean.TRUE;

        String dir = Long.toString(memberId);
        String extension = Optional.ofNullable(file)
                .map(MultipartFile::getOriginalFilename)
                .map(name -> name.substring(name.lastIndexOf(".")).toLowerCase())
                .orElse("default_extension");

        String newFileName = dir + extension;

        try {
            //사업자등록증 폴더안에 memberId로 된 폴더에 이미지를 저장
            File folder = new File("img" + File.separator + "사업자등록증" + File.separator + dir);
            File[] files = folder.listFiles();
            if (!folder.exists()) {
                folder.mkdirs();
            } else if (files != null) {
                for (File file1 : files) {
                    file1.delete();
                }
            }
            File destination = new File( folder.getAbsolutePath() , newFileName);

            file.transferTo(destination);

            result = Boolean.FALSE;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public List<Member> getMembersByPosition(Position position) {
        return memberRepository.findByPosition(position);
    }

//    public Member updateMemberRole(Long memberId, Long companyId, MemberDto.Roles roles) {
//
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//
//        Company company = member.getCompany();
//
//        if (company.getCompanyId().equals(companyId)) {
//            member.setRoles(mapper.memberPatchToRole(roles));
//            return memberRepository.save(member);
//        } else {
//            throw new BusinessLogicException(ExceptionCode.COMPANY_NOT_FOUND);
//        }
//    }

}
