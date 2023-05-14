package main.main.member.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.auth.utils.CustomAuthorityUtils;
import main.main.dto.ListPageResponseDto;
import main.main.member.dto.MemberDto;
import main.main.member.dto.Position;
import main.main.member.entity.Member;
import main.main.member.mapper.MemberMapper;
import main.main.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.io.IOUtils;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        Member createdMember = memberService.createMember(member);
        URI location = UriComponentsBuilder
                .newInstance()
                .path(MEMBER_DEFAULT_URL + "{member-id}")
                .buildAndExpand(createdMember.getMemberId())
                .toUri();

        return ResponseEntity.created(location).build();


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
//        memberService.updateMember(mapper.responserPatchToMember(requestBody, authenticationMemberId));

        Member member = mapper.responserPatchToMember(requestBody, authenticationMemberId);

        Position position = member.getPosition();
        if (position == null) {
            position = Position.STAFF;
        }

//        member.setRoles(Arrays.asList(position.getRole()));

        memberService.updateMember(member);
        return new ResponseEntity<>(mapper.memberPatchToMember(memberService.findMember(memberId)), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        memberService.deleteMember(memberId, authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page, @RequestParam int size) {

        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(
                mapper.membersToMemberResponses(members), pageMembers)
                , HttpStatus.OK);
    }

    @PostMapping(path = "/upload/{member-id}")
    public ResponseEntity postImageUpload(@PathVariable("member-id") Long memberId,
                                          @RequestPart(required = false) MultipartFile file){
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        String dir = Long.toString(memberId);

        memberService.uploading(file, memberId, authenticationMemberId, dir);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/image/{member-id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("member-id") long memberId) throws IOException {
        String dir = Long.toString(memberId);
        String fileExtension = ".png";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream( "img" + "/" + "사업자등록증" + "/" + dir + "/" + dir + ".jpeg");
        } catch (Exception e) {
            inputStream = new FileInputStream( "img" + "/" + "사업자등록증" + "/" + dir + "/" + dir + ".png");
        } finally {
            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();

            HttpHeaders httpHeaders = new HttpHeaders();
            if (".png".equals(fileExtension)) {
                httpHeaders.setContentType(MediaType.IMAGE_PNG);
            } else if (".jpeg".equals(fileExtension)) {
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            } return new ResponseEntity<>(imageByteArray, httpHeaders, HttpStatus.OK);
        }
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<Member>> getMembersByPosition(@PathVariable String position) {
        Position pos = Position.valueOf(position);
        List<Member> members = memberService.getMembersByPosition(pos);
        return ResponseEntity.ok(members);
    }

//    @PatchMapping("/{company-id}/{member-id}/role")
//    public ResponseEntity<Member> updateMemberRole(
//            @PathVariable("member-id") Long memberId,
//            @PathVariable("company-id") Long companyId,
//            @RequestBody MemberDto.Roles roles) {
//
//        memberService.updateMemberRole(memberId, companyId, roles);
//
//        return new ResponseEntity(mapper.memberPatchToMember(memberService.findMember(memberId)), HttpStatus.OK);
//    }
//
//    @PatchMapping("/transfer/{member-id}/{company-id}")
//    public ResponseEntity companyTransfer(@PathVariable("member-id") Long memberId,
//                                          @PathVariable("company-id") Long companyId) {
//
//        return new ResponseEntity(mapper.memberToMembersResponses(
//                memberService.companyMember(memberId, companyId)), HttpStatus.OK);
//    }


}
