package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.CreateUserRequest;
import com.bank.dealpipeline.dto.UpdateUserStatusRequest;
import com.bank.dealpipeline.model.User;
import com.bank.dealpipeline.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 1️ CREATE USER (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public String createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return "User created successfully";
    }

    // 2️ LIST USERS (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 3️ ACTIVATE / DEACTIVATE USER (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/status")
    public String updateUserStatus(@PathVariable String id, @Valid @RequestBody UpdateUserStatusRequest request) {
        userService.updateUserStatus(id, request.isActive());
        return "User status updated";
    }
}
