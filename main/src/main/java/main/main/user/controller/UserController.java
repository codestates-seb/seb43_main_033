package main.main.user.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.user.dto.UserDto;
import main.main.user.entity.User;
import main.main.user.mapper.UserMapper;
import main.main.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final static String USER_DEFAULT_URL = "/users";

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post requestBody) {
        User user = mapper.userPostToUser(requestBody);

        userService.createUser(user);

        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@Positive @PathVariable("user-id") Long userId) {
        return new ResponseEntity<>(mapper.userToUserResponse(userService.findUser(userId)), HttpStatus.OK);
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@Positive @PathVariable("user-id") Long userId,
                                    @Valid @RequestBody UserDto.Patch requestBody) {
        long authenticationUserId = JwtParseInterceptor.getAutheticatedUserId();
        requestBody.setUserId(userId);
        userService.updateUser(mapper.resposerPatchToUser(requestBody, authenticationUserId));
        return new ResponseEntity<>(mapper.userPatchToUser(userService.findUser(userId)), HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@Positive @PathVariable("user-id") Long userId) {
        long authenticationUserId = JwtParseInterceptor.getAutheticatedUserId();
        userService.deleteUser(userId, authenticationUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
