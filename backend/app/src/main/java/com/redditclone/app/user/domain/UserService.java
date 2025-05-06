package com.redditclone.app.user.domain;

import com.redditclone.app.user.application.UserRegistrationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registerUser(UserRegistrationRequest registrationRequest);
}
