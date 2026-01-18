package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.CreateUserRequest;
import com.bank.dealpipeline.dto.UpdateUserStatusRequest;
import com.bank.dealpipeline.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void createUser() {
        CreateUserRequest request = mock(CreateUserRequest.class);

        when(userService.createUser(request)).thenReturn(null);

        String result = adminController.createUser(request);

        assert(result.contains("successfully"));
    }

    @Test
    void updateUserStatus() {
        UpdateUserStatusRequest request = mock(UpdateUserStatusRequest.class);

        when(request.isActive()).thenReturn(true);

        adminController.updateUserStatus("1", request);

        verify(userService).updateUserStatus("1", true);
    }

    @Test
    void getAllUsers() {
        adminController.getAllUsers();
        verify(userService).getAllUsers();
    }
}
