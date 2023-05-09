package main.main.companymember.controller;

import lombok.RequiredArgsConstructor;
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
        CompanyMember companyMember = companyMemberService.createCompanyMember(companyMemberMapper.companyMemberPostToCompanyMember(requestBody));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{companymember-id}")
    public ResponseEntity patchCompanyMember(@Positive @PathVariable("companymember-id") long companyMemberId,
                                             @Valid @RequestBody CompanyMemberDto.Patch requestBody) {
        requestBody.setCompanyMemberId(companyMemberId);
        companyMemberService.updateCompanyMember(companyMemberMapper.companyMemberPacthToCompanyMember(requestBody));
        return new ResponseEntity<>(companyMemberMapper.companyMemberToCompanyMemberResponse(companyMemberService.findCompanyMember(companyMemberId)), HttpStatus.OK);
    }

    @GetMapping("/{companymember-id}")
    public ResponseEntity getCompanyMember(@Positive @PathVariable("companymember-id") long companyMemberId) {
        CompanyMember companyMember = companyMemberService.findCompanyMember(companyMemberId);
        return new ResponseEntity<>(companyMemberMapper.companyMemberToCompanyMemberResponse(companyMember), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCompanyMembers(@Positive @RequestParam int page, @RequestParam int size) {
        Page<CompanyMember> pageCompanyMembers = companyMemberService.findCompanyMembers(page - 1, size);
        List<CompanyMember> companyMembers = pageCompanyMembers.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(companyMemberMapper.companyMembersToCompanyMembersResponse(companyMembers), pageCompanyMembers), HttpStatus.OK);
    }


    @DeleteMapping("/{companymember-id}")
    public ResponseEntity deleteCompanyMember(@Positive @PathVariable("compnaymember-id") long companyMemberId) {
        companyMemberService.deleteCompanyMember(companyMemberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
