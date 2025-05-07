package com.redditclone.app.user.domain;

import com.redditclone.app.user.application.LoginRequestDTO;
import com.redditclone.app.user.application.RegistrationRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registerUser(RegistrationRequestDTO registrationRequest);
    UserDetails loadUserByUsername(String username);
}
