package com.bnp.onlinebookstore.controller;

import com.bnp.onlinebookstore.dto.UserRegisterRequest;
import com.bnp.onlinebookstore.exception.UserAlreadyExistsException;
import com.bnp.onlinebookstore.service.UserRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserRegistrationControllerTest {

    @Mock
    private UserRegistrationService userRegistrationService;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(userRegistrationController).build();
    }

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        // Given
        UserRegisterRequest user = new UserRegisterRequest("newUser","password");
        ObjectMapper mapper=new ObjectMapper();
        String userRequestString=mapper.writeValueAsString(user);
        // When
        mockMvc.perform(post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestString))
                .andExpect(status().isCreated());

        // Verify that the service method was called
        Mockito.verify(userRegistrationService).register(user);
    }

    @Test
    public void shouldThrowUsernameAlreadyExistsException() throws Exception {
        // Given
        UserRegisterRequest user = new UserRegisterRequest("newUser","password");

        // Mock the behavior of the userRegistrationService
        Mockito.doThrow(new UserAlreadyExistsException("Username 'existingUser' already exists."))
                .when(userRegistrationService).register(user);

        ObjectMapper mapper=new ObjectMapper();
        String userRequestString=mapper.writeValueAsString(user);

        // When & Then
        mockMvc.perform(post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestString))
                .andExpect(status().isConflict());
        // Verify that the service method was called
        Mockito.verify(userRegistrationService).register(user);
    }
}
