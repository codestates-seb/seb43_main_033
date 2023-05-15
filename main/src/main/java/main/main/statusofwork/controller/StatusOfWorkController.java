package main.main.statusofwork.controller;

import lombok.RequiredArgsConstructor;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statusofworks")
@RequiredArgsConstructor
public class StatusOfWorkController {
    private final StatusOfWorkService statusOfWorkService;
    private final StatusOfWorkMapper statusOfWorkMapper;

    @PostMapping
    public ResponseEntity postStatusOfWork(@RequestBody StatusOfWorkDto.Post requestBody) {
        statusOfWorkService.createStatusOfWork(statusOfWorkMapper.postToStatusOfWork(requestBody));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{statusofwork-id}")
    public ResponseEntity patchStatusOfWork(@PathVariable("statusofwork-id") long statusOfworkId,
                                            @RequestBody StatusOfWorkDto.Patch requestBody) {
        statusOfWorkService.updateStatusOfWork(statusOfworkId, statusOfWorkMapper.patchToStatusOfWork(requestBody));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getStatusOfWork(@RequestParam int year, @RequestParam int month,
                                          @PathVariable("member-id") long memberId) {
        List<StatusOfWork> statusOfWorks = statusOfWorkService.findStatusOfWorks(year, month, memberId);

        return new ResponseEntity<>(statusOfWorkMapper.statusOfWorksToResponses(statusOfWorks), HttpStatus.OK);
    }

    @DeleteMapping("/{statusofwork-id}")
    public ResponseEntity deleteStatusOfWork(@PathVariable("statusofwork-id") long stausOfworkId) {
        statusOfWorkService.deleteStatusOfWork(stausOfworkId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
