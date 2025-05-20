package com.redditclone.app.user.presentation;

import com.redditclone.app.shared.security.AuthService;
import com.redditclone.app.user.application.dto.LoginRequestDTO;
import com.redditclone.app.user.application.dto.PasswordChangeRequestDTO;
import com.redditclone.app.user.application.dto.RegistrationRequestDTO;
import com.redditclone.app.user.domain.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@Validated
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String token = authService.loginUser(loginRequest);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequestDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
