package main.main.user.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.user.dto.MemberDto;
import main.main.user.entity.Member;
import main.main.user.mapper.MemberMapper;
import main.main.user.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final static String MEMBER_DEFAULT_URL = "/members";

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);

        memberService.createMember(member);

        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") Long memberId) {
        return new ResponseEntity<>(mapper.memberToMemberResponse(memberService.findMember(memberId)), HttpStatus.OK);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") Long memberId,
                                    @Valid @RequestBody MemberDto.Patch requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        requestBody.setMemberId(memberId);
        memberService.updateMember(mapper.responserPatchToMember(requestBody, authenticationMemberId));
        return new ResponseEntity<>(mapper.memberPatchToMember(memberService.findMember(memberId)), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        memberService.deleteMember(memberId, authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
