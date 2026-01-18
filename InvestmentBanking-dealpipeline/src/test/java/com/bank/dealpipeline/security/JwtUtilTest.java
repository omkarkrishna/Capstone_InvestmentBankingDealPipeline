package com.bank.dealpipeline.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();

    @Test
    void generateAndValidateToken() {
        String token = jwtUtil.generateToken("john", "ADMIN");

        assertTrue(jwtUtil.isTokenValid(token));
        assertEquals("john", jwtUtil.extractUsername(token));
        assertEquals("ADMIN", jwtUtil.extractRole(token));
    }
}
