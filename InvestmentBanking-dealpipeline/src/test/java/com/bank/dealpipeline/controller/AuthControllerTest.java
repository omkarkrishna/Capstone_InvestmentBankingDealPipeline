package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.LoginRequest;
import com.bank.dealpipeline.dto.LoginResponse;
import com.bank.dealpipeline.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login() {
        LoginRequest request = mock(LoginRequest.class);

        when(request.getUsername()).thenReturn("john");
        when(request.getPassword()).thenReturn("pass");
        when(authService.login("john", "pass"))
                .thenReturn(new LoginResponse("jwt", "USER"));

        LoginResponse response = authController.login(request);

        assertEquals("jwt", response.getToken());
        assertEquals("USER", response.getRole());
    }
}
