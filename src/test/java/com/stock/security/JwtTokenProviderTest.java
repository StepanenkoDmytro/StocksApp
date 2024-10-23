package com.stock.security;

import com.stock.model.role.Role;
import com.stock.model.user.User;
import com.stock.security.jwt.JwtAuthenticationException;
import com.stock.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {
    @Mock
    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;
    private final String SECRET = "1234567890987654321";
    private static final long VALIDITY_MILLISECONDS = 3600;
    private final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1NjE1MDYzLCJleHAiOjE2ODU2MTUwNjd9.aogefZWqEjpgyKPkw9iZ1ciSV5mGa_ZJs9ASZfYeQhw";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(userDetailsService, SECRET, VALIDITY_MILLISECONDS);
    }

    @Test
    void generateAndParseToken() {
        User user = createUserWithRole("email@email.com", "ROLE_USER");

        String token = jwtTokenProvider.createToken(user);
        assertNotNull(token);

        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);

        assertEquals(user.getEmail(), jwtTokenProvider.getEmailFromToken(token));
        assertTrue(claims.getBody().containsKey("roles"));
        assertEquals(Collections.singletonList("ROLE_USER"), claims.getBody().get("roles"));
    }

    @Test
    void resolveToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer_" + TEST_TOKEN);

        String requestToken = jwtTokenProvider.resolveToken(request);
        assertEquals(TEST_TOKEN, requestToken);
    }

    @Test
    void notValidTokenReturnFalse() {
        assertFalse(jwtTokenProvider.isTokenExpired(TEST_TOKEN));
    }

    @Test
    void notValidTokenThrowsException() {
        assertThrows(JwtAuthenticationException.class, () -> jwtTokenProvider.isValidateToken(TEST_TOKEN));
    }

    private User createUserWithRole(String email, String roleUser) {
        User user = new User();
        user.setEmail(email);
        Role role = new Role();
        role.setName(roleUser);
        user.setRoles(Collections.singletonList(role));
        return user;
    }
}
