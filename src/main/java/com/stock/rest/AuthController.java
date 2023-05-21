package com.stock.rest;

import com.stock.dto.AuthDto;
import com.stock.dto.SignUpDto;
import com.stock.dto.UserDto;
import com.stock.model.user.User;
import com.stock.security.jwt.JwtTokenProvider;
import com.stock.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin
@PropertySource("classpath:security-keys.properties")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;
    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("sign-in")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        User user = userService.getUserByEmail(authDto.getEmail());

        if (user != null) {
            String email = user.getEmail();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, authDto.getPassword()));

            String token = jwtTokenProvider.createToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("user", UserDto.mapUserToUserDto(user));
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("sign-up")
    public ResponseEntity registerUser(@RequestBody SignUpDto newUser) {
        if (userService.isUserExistByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        if (userService.isUserExistByUsername(newUser.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        User user = SignUpDto.signUpToUser(newUser);
        userService.registration(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("refresh-token")
    public ResponseEntity resolveToken(@RequestBody String token) {
        System.out.println("Should refresh: " + token);
        if (jwtTokenProvider.isValidateToken(token)) {
            String email = jwtTokenProvider.getUsername(token);
            User user = userService.getUserByEmail(email);
            System.out.println("I am in if");
            String newToken = jwtTokenProvider.createToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("user", UserDto.mapUserToUserDto(user));
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        System.out.println("I am UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired or invalid");
    }
}
