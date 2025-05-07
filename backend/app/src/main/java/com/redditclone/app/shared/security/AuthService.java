package com.redditclone.app.shared.security;

import com.redditclone.app.user.application.LoginRequestDTO;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import com.redditclone.app.user.domain.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public String loginUser(LoginRequestDTO loginRequest){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            User user = (User) userService.loadUserByUsername(loginRequest.getUsername());

            String token = jwtService.generateToken(user);

            return token;
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid username or password", ex);
        }
    }

}
