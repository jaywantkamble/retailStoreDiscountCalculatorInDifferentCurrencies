package com.retailstorediscount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.retailstorediscount.util.JwtUtil;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // JWT tokens typically start with eyJ (base64-encoded)
    }

    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.validateToken(token, username));
    }

    
    @Test
    void testCreateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtUtil.getSecretKeySpec())
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, claims.getSubject());
    }
}
