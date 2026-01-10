package com.bank.dealpipeline.service;

import com.bank.dealpipeline.dto.CreateUserRequest;
import com.bank.dealpipeline.model.Role;
import com.bank.dealpipeline.model.User;
import com.bank.dealpipeline.repository.UserRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void create_user_success() {
        CreateUserRequest request = mock(CreateUserRequest.class);

        when(request.getUsername()).thenReturn("admin");
        when(request.getEmail()).thenReturn("admin@bank.com");
        when(request.getPassword()).thenReturn("pass");
        when(request.getRole()).thenReturn(Role.ADMIN);

        when(passwordEncoder.encode("pass"))
                .thenReturn("hashed");
        when(userRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        User user = userService.createUser(request);

        assertEquals("admin", user.getUsername());
        assertTrue(user.isActive());
    }

    @Test
    void update_user_status() {
        User user = new User();
        user.setActive(true);

        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));

        userService.updateUserStatus("1", false);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }
}
