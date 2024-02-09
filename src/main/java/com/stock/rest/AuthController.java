package com.stock.rest;

//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.stock.dto.auth.AuthDto;
//import com.stock.dto.auth.GoogleSignUpDto;
import com.stock.dto.auth.SignUpDto;
//import com.stock.dto.accountDtos.UserDto;
import com.stock.dto.auth.UserDtoResponse;
//import com.stock.helper.SocialMediaHelper;
//import com.stock.helper.dto.GoogleUserDto;
import com.stock.model.user.Role;
import com.stock.model.user.User;
import com.stock.security.jwt.JwtTokenProvider;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin
@PropertySource("classpath:security-keys.properties")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
//    private final SocialMediaHelper socialMediaHelper;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider
//            , SocialMediaHelper socialMediaHelper
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
//        this.socialMediaHelper = socialMediaHelper;
    }

    @PostMapping("sign-in")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        String email = authDto.getEmail();

        if (authDto.getPassword().length() == 0) {
            return ResponseEntity.badRequest().body("Invalid params: password cannot be null");
        }
        User user = userService.getFullUserByEmail(email);
        try {
            System.out.println(authDto.toString());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, authDto.getPassword(), mapToGrantedAuthority(user.getRoles())));
        } catch (Exception exp) {
            System.out.println(exp);
            return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED);
        }
        UserDtoResponse response = generateUserDtoResponse(email);

        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto newUser) {
        if (newUser.getPassword().length() == 0) {
            return ResponseEntity.badRequest().body("Invalid params: password cannot be null");
        }

        if (userService.isUserExistByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already exist!");
        }
        if (userService.isUserExistByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Username is already exist!");
        }
        User user = SignUpDto.signUpToUser(newUser);
        userService.registration(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @PostMapping("google-sign-up")
//    public ResponseEntity<?> registerGoogleUser(@RequestBody GoogleSignUpDto newUser) {
//        GoogleUserDto googleUser = null;
//        try {
//            googleUser = socialMediaHelper.getEmailFromGoogleToken(newUser.getToken());
//            String emailUser = googleUser.getEmail();
//
//            if(googleUser.isEmailVerify()) {
//                if(!userService.isUserExistByEmail(emailUser)) {
//                    User user = SignUpDto.signUpGoogleToUser(googleUser);
//                    userService.registration(user);
//                }
//
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(emailUser, null));
//
//                UserDtoResponse response = generateUserDtoResponse(emailUser);
//                return ResponseEntity.ok(response);
//            }
//        } catch (GeneralSecurityException | IOException | AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    private UserDtoResponse generateUserDtoResponse(String email) {
        User user = userService.getFullUserByEmail(email);
        String token = jwtTokenProvider.createToken(user);
        return UserDtoResponse.mapUserDtoResponse(user, token);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String token) {
        if (token != null && jwtTokenProvider.isTokenExpired(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            User user = userService.getFullUserByEmail(email);

            String newToken = jwtTokenProvider.createToken(user);

            UserDtoResponse response = UserDtoResponse.mapUserDtoResponse(user, newToken);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired or invalid");
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
