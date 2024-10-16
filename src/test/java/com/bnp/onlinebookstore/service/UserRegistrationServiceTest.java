package com.bnp.onlinebookstore.service;

import com.bnp.onlinebookstore.dto.UserRegisterRequest;
import com.bnp.onlinebookstore.model.User;
import com.bnp.onlinebookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegistrationService userRegistrationService;


    @Test
    public void shouldRegisterNewUser(){
        //given
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest("user1","password1");
        Mockito.when(userRepository.findByUserName("user1")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password1")).thenReturn("password1");
        //when
        userRegistrationService.register(userRegisterRequest);
        //then
        ArgumentCaptor<User> userCaptor=ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userCaptor.capture());
        assertEquals("user1",userCaptor.getValue().getUserName());
        assertEquals("password1",userCaptor.getValue().getPassword());
    }

    @Test
    public void shouldThrowExceptionIfUserAlreadyExists(){
        //given
        UserRegisterRequest userRegisterRequest =new UserRegisterRequest("user1","password1");
        Mockito.when(userRepository.findByUserName("user1")).thenReturn(Optional.of(new User()));

        //when
        assertThrows(RuntimeException.class,()->{
            userRegistrationService.register(userRegisterRequest);
        });
        //then
        Mockito.verify(userRepository,Mockito.never()).save(Mockito.any(User.class));
    }
}
