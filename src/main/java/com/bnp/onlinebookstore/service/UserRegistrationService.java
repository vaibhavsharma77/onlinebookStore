package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.dto.LoginRequest;
import com.bnp.onlinebookstore.dto.UserRegisterRequest;
import com.bnp.onlinebookstore.exception.UserAlreadyExistsException;
import com.bnp.onlinebookstore.model.User;
import com.bnp.onlinebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegisterRequest userRegisterRequest) {

        Optional<User> foundUser = userRepository.findByUserName(userRegisterRequest.getUserName());
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException("User is Already Present");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        User user = User.builder()
                .userName(userRegisterRequest.getUserName())
                .password(encodedPassword)
                .build();

        return userRepository.save(user);


    }

    public boolean isLoginAllowed(LoginRequest loginRequest){
        Optional<User> foundUser = userRepository.findByUserName(loginRequest.getUserName());
        if(foundUser.isPresent()){
            return passwordEncoder.matches(loginRequest.getPassword(), foundUser.get().getPassword());
        }
        return false;
    }
}
