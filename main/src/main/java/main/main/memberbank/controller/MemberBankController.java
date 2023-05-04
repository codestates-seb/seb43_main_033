package main.main.memberbank.controller;


import lombok.RequiredArgsConstructor;
import main.main.memberbank.dto.MemberBankDto;
import main.main.memberbank.entity.MemberBank;
import main.main.memberbank.mapper.MemberBankMapper;
import main.main.memberbank.service.MemberBankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RequestMapping("/memberbanks")
@RestController
@RequiredArgsConstructor
public class MemberBankController {

    private final static String MEMBERBANK_DEFAULT_URL = "/memberbanks";
    private final MemberBankService memberBankService;
    private final MemberBankMapper memberBankMapper;

    @PostMapping
    public  ResponseEntity postMemberBank(@Valid @RequestBody MemberBankDto.Post requestBody) {
        MemberBank memberBank = memberBankService.createMemberBank(memberBankMapper.memberBankPostToMemberBank(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{memberbank-id}")
    public ResponseEntity patchMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId,
                                          @Valid @RequestBody MemberBankDto.Patch requestBody) {
        requestBody.setMemberBankId(memberBankId);
        memberBankService.updateMemberBank(memberBankMapper.memberBankPathToMemberBank(requestBody));
        return new ResponseEntity<>(memberBankMapper.memberBankToMemberBankResponse(memberBankService.findMemberBank(memberBankId)), HttpStatus.OK);

    }

    @GetMapping("/{memberbank-id}")
    public ResponseEntity getMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId) {
        MemberBank memberBank = memberBankService.findMemberBank(memberBankId);

        return new ResponseEntity<>(memberBankMapper.memberBankToMemberBankResponse(memberBank), HttpStatus.OK);
    }

    @DeleteMapping("/{memberbank-id}")
    public ResponseEntity deleteMemberBank(@Positive @PathVariable("memberbank-id") long memberBankId) {
        memberBankService.deleteMemberBank(memberBankId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
