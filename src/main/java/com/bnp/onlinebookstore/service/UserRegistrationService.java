package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.dto.UserRegisterRequest;
import com.bnp.onlinebookstore.model.User;
import com.bnp.onlinebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User register(UserRegisterRequest userRegisterRequest) {

        Optional<User> foundUser = userRepository.findByUserName(userRegisterRequest.getUserName());
        if (foundUser.isPresent()) {
            throw new RuntimeException("User is Already Present");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        User user = User.builder()
                .userName(userRegisterRequest.getUserName())
                .password(encodedPassword)
                .build();

        return userRepository.save(user);


    }
}
