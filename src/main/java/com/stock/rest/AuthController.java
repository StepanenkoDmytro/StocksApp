package com.stock.rest;

import com.stock.dto.AuthDto;
import com.stock.dto.SignUpDto;
import com.stock.model.user.User;
import com.stock.security.jwt.JwtTokenProvider;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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

            Map<String, String> response = new HashMap<>();
            response.put("username", email);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("sign-up")
    public ResponseEntity registerUser(@RequestBody SignUpDto newUser) {
        if(userService.isUserExistByEmail(newUser.getEmail())){
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        if(userService.isUserExistByUsername(newUser.getUsername())){
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        User user = SignUpDto.signUpToUser(newUser);
        userService.registration(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
