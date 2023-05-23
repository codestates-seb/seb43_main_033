package main.main.statusofwork.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.service.CompanyMemberService;
import main.main.dto.ListPageResponseDto;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.dto.VacationDto;
import main.main.statusofwork.entity.RequestVacation;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatusOfWorkController {
    private final StatusOfWorkService statusOfWorkService;
    private final CompanyMemberService companyMemberService;
    private final StatusOfWorkMapper statusOfWorkMapper;

    @PostMapping("/manager/{company-id}/members/{companymember-id}/status")
    public ResponseEntity postStatusOfWork(@RequestBody StatusOfWorkDto.Post requestBody,
                                           @PathVariable("company-id") long companyId,
                                           @PathVariable("companymember-id") long companyMemberId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        StatusOfWork statusOfWork = statusOfWorkMapper.postToStatusOfWork(requestBody);
        statusOfWorkService.createStatusOfWork(statusOfWork, companyId, companyMemberId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/worker/mywork")
    public ResponseEntity postStatusOfWorkWorker(@RequestBody StatusOfWorkDto.WPost requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        StatusOfWork statusOfWork = statusOfWorkMapper.postToStatusOfWork(requestBody);
        statusOfWorkService.createStatusOfWork(statusOfWork, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/worker/mywork/vacations")
    public ResponseEntity requestVacation(@RequestBody VacationDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        statusOfWorkService.requestVacation(statusOfWorkMapper.postToRequestVacation(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/manager/vacations/{vacation-id}/{status}")
    public ResponseEntity reviewVacation(@PathVariable("vacation-id") long requestId,
                                         @PathVariable("status") String status) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        statusOfWorkService.reviewRequestVacation(requestId, status, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/status/{statusofwork-id}")
    public ResponseEntity patchStatusOfWork(@PathVariable("statusofwork-id") long statusOfworkId,
                                            @RequestBody StatusOfWorkDto.Patch requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        statusOfWorkService.updateStatusOfWork(statusOfworkId, statusOfWorkMapper.patchToStatusOfWork(requestBody), authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/manager/{company-id}/vacations")
    public ResponseEntity getVacationRequestList(@PathVariable("company-id") long companyId) {
        List<RequestVacation> requestList = statusOfWorkService.getRequestList(companyId);

        return new ResponseEntity<>(statusOfWorkMapper.requestResponses(requestList), HttpStatus.OK);
    }

    @GetMapping("/manager/{company-id}/mystaff")
    public ResponseEntity getTodayStatusOfWork(@PathVariable("company-id") long companyId, @Positive @RequestParam int page) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        Page<CompanyMember> pageCompanyMember = companyMemberService.findCompanyMembersByCompanyId(page - 1, companyId);
        List<CompanyMember> companyMembers = statusOfWorkService.findTodayStatusOfWorks(pageCompanyMember.getContent(), companyId, authenticationMemberId);

        return new ResponseEntity<>(new ListPageResponseDto<>(statusOfWorkMapper.todayToResponse(companyMembers), pageCompanyMember), HttpStatus.OK);
    }


    @GetMapping("/worker/mywork")
    public ResponseEntity getStatusOfWork(@RequestParam int year, @RequestParam int month) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        List<StatusOfWork> statusOfWorks = statusOfWorkService.findStatusOfWorks(year, month, 1, authenticationMemberId);

        return new ResponseEntity<>(statusOfWorkMapper.statusOfWorkToMyWork(statusOfWorks), HttpStatus.OK);
    }

    @DeleteMapping("/status/{statusofwork-id}")
    public ResponseEntity deleteStatusOfWork(@PathVariable("statusofwork-id") long stausOfworkId) {
        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();
        statusOfWorkService.deleteStatusOfWork(stausOfworkId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
