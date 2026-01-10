package com.bank.dealpipeline.service;

import com.bank.dealpipeline.dto.LoginResponse;
import com.bank.dealpipeline.model.Role;
import com.bank.dealpipeline.model.User;
import com.bank.dealpipeline.repository.UserRepository;
import com.bank.dealpipeline.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_success() {
        User user = mock(User.class);

        when(user.getUsername()).thenReturn("john");
        when(user.getPassword()).thenReturn("hashed");
        when(user.getRole()).thenReturn(Role.USER);
        when(user.isActive()).thenReturn(true);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "hashed"))
                .thenReturn(true);
        when(jwtUtil.generateToken("john", "USER"))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login("john", "pass");

        assertEquals("jwt-token", response.getToken());
        assertEquals("USER", response.getRole());
    }

    @Test
    void login_user_not_found() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> authService.login("john", "pass"));
    }

    @Test
    void login_inactive_user() {
        User user = mock(User.class);
        when(user.isActive()).thenReturn(false);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> authService.login("john", "pass"));
    }

    @Test
    void login_invalid_password() {
        User user = mock(User.class);

        when(user.getPassword()).thenReturn("hashed");
        when(user.isActive()).thenReturn(true);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> authService.login("john", "wrong"));
    }
}
