package main.main.employer.controller;

import lombok.RequiredArgsConstructor;
import main.main.employer.mapper.UserrMapper;
import main.main.employer.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final static String USER_DEFAULT_URL = "/users";

    private final UserService userService;
    private final UserrMapper mapper;



}
