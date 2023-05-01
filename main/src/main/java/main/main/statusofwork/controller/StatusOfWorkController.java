package main.main.statusofwork.controller;

import lombok.RequiredArgsConstructor;
import main.main.statusofwork.mapper.StatusOfWorkMapper;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statusofwork")
@RequiredArgsConstructor
public class StatusOfWorkController {
    private final StatusOfWorkService statusOfWorkService;
    private final StatusOfWorkMapper statusOfWorkMapper;
}
