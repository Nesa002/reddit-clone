package com.redditclone.app.user.presentation;

import com.redditclone.app.user.application.UserRegistrationRequest;
import com.redditclone.app.user.domain.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
