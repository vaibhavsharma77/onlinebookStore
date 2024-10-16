package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.LoginRequest;
import com.bnp.onlinebookstore.dto.UserRegisterRequest;
import com.bnp.onlinebookstore.exception.UserAlreadyExistsException;
import com.bnp.onlinebookstore.model.User;
import com.bnp.onlinebookstore.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> userRegistration(@RequestBody UserRegisterRequest userRegisterRequest) {
        User user = userRegistrationService.register(userRegisterRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> isLoginValid(@RequestBody LoginRequest loginRequest){
        boolean isValid = userRegistrationService.isLoginAllowed(loginRequest);
        if(!isValid){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok("Logic Successful");
    }

    // Local exception handler for UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        // Return HTTP 409 Conflict with the exception message
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
