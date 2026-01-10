package com.bank.dealpipeline.controller;

import com.bank.dealpipeline.dto.LoginRequest;
import com.bank.dealpipeline.dto.LoginResponse;
import com.bank.dealpipeline.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(
                request.getUsername(),
                request.getPassword()
        );
    }
}
