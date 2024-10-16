package com.bnp.onlinebookstore.repository;

import com.bnp.onlinebookstore.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUserName() {
        //given
        User dummyUser = User.builder()
                .userName("dummyUser-1")
                .password("EncyrptedPassword@12")
                .build();
        Mockito.when(userRepository.findByUserName("dummyUser-1")).thenReturn(Optional.of(dummyUser));
        //when
        Optional<User> foundUser = userRepository.findByUserName("dummyUser-1");
        assertTrue(foundUser.isPresent());
        assertEquals("dummyUser-1", foundUser.get().getUserName());
        Mockito.verify(userRepository, times(1)).findByUserName("dummyUser-1");
    }

    @Test
    public void shouldReturnEmptyIfUserNameNotFound() {
        //given
        Mockito.when(userRepository.findByUserName(any())).thenReturn(Optional.empty());
        //when
        Optional<User> foundUser = userRepository.findByUserName("randomUser");
        assertFalse(foundUser.isPresent());
        Mockito.verify(userRepository, times(1)).findByUserName("randomUser");
    }

}
