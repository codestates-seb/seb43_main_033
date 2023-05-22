package main.main.companymember.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.mapper.CompanyMemberMapper;
import main.main.companymember.service.CompanyMemberService;
import main.main.dto.ListPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/companymembers")
@RestController
@RequiredArgsConstructor
public class CompanyMemberController {

    private final CompanyMemberService companyMemberService;
    private final CompanyMemberMapper companyMemberMapper;

    @PostMapping
    public ResponseEntity postCompanyMember(@Valid @RequestBody CompanyMemberDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        CompanyMember companyMember = companyMemberService.createCompanyMember(companyMemberMapper.companyMemberPostToCompanyMember(requestBody), authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{companymember-id}")
    public ResponseEntity patchCompanyMember(@Positive @PathVariable("companymember-id") long companyMemberId,
                                             @Valid @RequestBody CompanyMemberDto.Patch requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        requestBody.setCompanyMemberId(companyMemberId);
        companyMemberService.updateCompanyMember(companyMemberMapper.companyMemberPacthToCompanyMember(requestBody), authenticationMemberId);
        companyMemberService.updateCompanyMemberRole(companyMemberId, requestBody);

        return new ResponseEntity<>(companyMemberMapper.companyMemberToCompanyMemberResponse(companyMemberService.findCompanyMember(companyMemberId)), HttpStatus.OK);
    }

    @GetMapping("/{companymember-id}")
    public ResponseEntity getCompanyMember(@Positive @PathVariable("companymember-id") long companyMemberId) {
        CompanyMember companyMember = companyMemberService.findCompanyMember(companyMemberId);
        return new ResponseEntity<>(companyMemberMapper.companyMemberToCompanyMemberResponse(companyMember), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCompanyMembers(@Positive @RequestParam int page, @RequestParam String status, @RequestParam Long companyId) {
        Page<CompanyMember> pageCompanyMembers = companyMemberService.findCompanyMembersByCompanyId(page - 1, status, companyId);
        List<CompanyMember> companyMembers = pageCompanyMembers.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(companyMemberMapper.companyMembersToCompanyMembersResponse(companyMembers), pageCompanyMembers), HttpStatus.OK);
    }


    @DeleteMapping("/{companymember-id}")
    public ResponseEntity deleteCompanyMember(@Positive @PathVariable("companymember-id") long companyMemberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        companyMemberService.deleteCompanyMember(companyMemberId, authenticationMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pending/{companymember-id}/{status}") // 승인 대기, 승인, 승인 거절
    public void pendingCompanyMember(@PathVariable("companymember-id") long companyMemberId
            , @PathVariable("status") String status) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        companyMemberService.companyMemberUpdate(companyMemberId, status, authenticationMemberId);
    }

}
