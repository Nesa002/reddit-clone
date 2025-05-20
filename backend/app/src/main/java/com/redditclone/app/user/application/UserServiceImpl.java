package com.redditclone.app.user.application;

import com.redditclone.app.shared.exception.DuplicateEmailException;
import com.redditclone.app.shared.exception.DuplicateUsernameException;
import com.redditclone.app.user.application.dto.PasswordChangeRequestDTO;
import com.redditclone.app.user.application.dto.RegistrationRequestDTO;
import com.redditclone.app.user.domain.User;
import com.redditclone.app.user.domain.UserRepository;
import com.redditclone.app.user.domain.UserRole;
import com.redditclone.app.user.domain.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(RegistrationRequestDTO registrationRequest) {
        boolean usernameExists = userRepository.findByUsername(registrationRequest.getUsername()).isPresent();
        if(usernameExists) {
            throw new DuplicateUsernameException("Username already exists");
        }

        boolean emailExists = userRepository.findByEmail(registrationRequest.getEmail()).isPresent();
        if(emailExists) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setBio(registrationRequest.getBio());
        user.setRole(UserRole.USER);

        String encodedPassword = bCryptPasswordEncoder.encode(registrationRequest.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public void changePassword(PasswordChangeRequestDTO passwordChangeRequestDTO) {
        Optional<User> userOptional=userRepository.findById(passwordChangeRequestDTO.getId());

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(passwordChangeRequestDTO.getPassword());
        User user=userOptional.get();
        user.setPassword(encodedPassword);
        userRepository.save(user);

    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }
}
