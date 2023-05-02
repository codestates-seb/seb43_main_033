package main.main.member.service;

import lombok.RequiredArgsConstructor;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.member.entity.Member;
import main.main.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member updateMember(Member member) {
        Member findedMember = findVerifiedMember(member.getMemberId());
        verifiyExitstResidentNumber(member.getResidentNumber());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findedMember.setName(member.getName()));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findedMember.setEmail(member.getEmail()));
        Optional.ofNullable(member.getPhoneNumber())
                .ifPresent(phoneNumber -> findedMember.setPhoneNumber(member.getPhoneNumber()));
        Optional.ofNullable(member.getResidentNumber())
                .ifPresent(residentNumber -> findedMember.setResidentNumber(member.getResidentNumber()));
        Optional.ofNullable(member.getAddress())
                .ifPresent(address -> findedMember.setAddress(member.getAddress()));
        Optional.ofNullable(member.getGrade())
                .ifPresent(grade -> findedMember.setGrade(member.getGrade()));

        return memberRepository.save(findedMember);
    }


    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member createMember(Member member) {
        verifiyExitstResidentNumber(member.getResidentNumber());

        return memberRepository.save(member);

    }

    private void verifiyExitstResidentNumber(String residentNumber) {
        Optional<Member> findedMemberByResidentNumber = memberRepository.findByResidentNumber(residentNumber);
        if (findedMemberByResidentNumber.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_RESIDENTNUMBER_EXISTS);
        }
    }

    public Member findVerifiedMember (Long memberId) {
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

    public void deletePermission(Member member, long authenticationUserId) {
        if (!member.getMemberId().equals(authenticationUserId) && !member.getEmail().equals("admin@gmail.com")) {
            throw new BusinessLogicException(ExceptionCode.ONLY_AUTHOR);
        }
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }
}
