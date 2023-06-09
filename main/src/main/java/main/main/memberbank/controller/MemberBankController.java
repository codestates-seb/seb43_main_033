package main.main.memberbank.controller;


import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.dto.ListPageResponseDto;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import main.main.memberbank.mapper.MemberBankMapper;
import main.main.memberbank.service.MemberBankService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/memberbanks")
@RestController
@RequiredArgsConstructor
public class MemberBankController {

    private final static String MEMBERBANK_DEFAULT_URL = "/memberbanks";
    private final MemberBankService memberBankService;
    private final MemberBankMapper memberBankMapper;

    @PostMapping
    public  ResponseEntity postMemberBank(@Valid @RequestBody MemberBankDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        MemberBank memberBank = memberBankService.createMemberBank(memberBankMapper.memberBankPostToMemberBank(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{memberbank-id}")
    public ResponseEntity patchMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId,
                                          @Valid @RequestBody MemberBankDto.Patch requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        requestBody.setMemberBankId(memberBankId);

        memberBankService.updateMemberBank(memberBankMapper.memberBankPathToMemberBank(requestBody), authenticationMemberId);

        MemberBank memberBank = memberBankService.findMemberBank(memberBankId);
        return new ResponseEntity<>(memberBankMapper.memberBankToMemberBankResponse(memberBank), HttpStatus.OK);
    }


    @GetMapping("/{memberbank-id}")
    public ResponseEntity getMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId) {
        MemberBank memberBank = memberBankService.findMemberBank(memberBankId);

        return new ResponseEntity<>(memberBankMapper.memberBankToMemberBankResponse(memberBank), HttpStatus.OK);
    }

    @GetMapping("/member/{member-id}")
    public ResponseEntity<List<MemberBankDto.ResponseForList>> getMemberBanksByMemberId(@Positive @PathVariable("member-id") long memberId) {
        List<MemberBank> memberBanks = memberBankService.findMemberBanksByMemberId(memberId);
        List<MemberBankDto.ResponseForList> memberBankResponses = memberBankMapper.memberBanksToMemberBanksResponse(memberBanks);
        return new ResponseEntity<>(memberBankResponses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMemberBanks(@Positive @RequestParam int page, @RequestParam int size) {
        Page<MemberBank> pageMemberBanks = memberBankService.findMemberBanks(page - 1, size);
        List<MemberBank> memberBanks = pageMemberBanks.getContent();

        return new ResponseEntity<>(new ListPageResponseDto<>(memberBankMapper.memberBanksToMemberBanksResponse(memberBanks), pageMemberBanks), HttpStatus.OK);
    }

    @DeleteMapping("/{memberbank-id}")
    public ResponseEntity deleteMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        memberBankService.deleteMemberBank(memberBankId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
