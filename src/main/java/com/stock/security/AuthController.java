package com.stock.security;

import com.stock.dto.auth.AuthDto;
import com.stock.dto.auth.SignUpDto;
import com.stock.dto.accountDtos.UserDto;
import com.stock.security.dto.GoogleSignUpDto;
import com.stock.security.dto.GoogleUserDto;
import com.stock.security.helper.SocialAuthHelper;
import com.stock.model.user.User;
import com.stock.security.dto.RecoveryCodeRequest;
import com.stock.security.dto.RecoveryCodeResponse;
import com.stock.security.dto.ResetPasswordRequest;
import com.stock.security.service.PasswordRecoveryService;
import com.stock.model.user.service.UserService;
import com.stock.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "*")
@PropertySource("classpath:security-keys.properties")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SocialAuthHelper socialMediaHelper;
    private final PasswordRecoveryService passwordRecoveryService;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;
    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider, SocialAuthHelper socialMediaHelper, PasswordRecoveryService passwordRecoveryService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.socialMediaHelper = socialMediaHelper;
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("sign-in")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        String email = authDto.getEmail();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, authDto.getPassword()));
        } catch (Exception exp) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }

        User user = userService.getFullUserByEmail(email);
        String token = jwtTokenProvider.createToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("user", UserDto.mapUserToUserDto(user));
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-up")
    public ResponseEntity registerUser(@RequestBody SignUpDto newUser) {
        if (userService.isUserExistByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already exist!");
        }
        if (userService.isUserExistByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body("Username is already exist!");
        }
        User user = SignUpDto.signUpToUser(newUser);
        userService.registration(user);
        String token = jwtTokenProvider.createToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("user", UserDto.mapUserToUserDto(user));
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("google-sign-up")
    public ResponseEntity<?> registerGoogleUser(@RequestBody GoogleSignUpDto newUser) {
        GoogleUserDto googleUser = null;
        try {
            googleUser = socialMediaHelper.getEmailFromGoogleToken(newUser.getToken());
            String emailUser = googleUser.getEmail();

            if (googleUser.isEmailVerify()) {
                if (!userService.isUserExistByEmail(emailUser)) {
                    User user = GoogleUserDto.signUpGoogleToUser(googleUser);
                    userService.registration(user);
                }

                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(emailUser, ""));

                User user = userService.getFullUserByEmail(emailUser);
                String token = jwtTokenProvider.createToken(user);
                Map<String, Object> response = new HashMap<>();
                response.put("user", UserDto.mapUserToUserDto(user));
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("refresh-token")
    public ResponseEntity refreshToken(@RequestBody String token) {
        if (token != null && jwtTokenProvider.isTokenExpired(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            User user = userService.getFullUserByEmail(email);

            String newToken = jwtTokenProvider.createToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("user", UserDto.mapUserToUserDto(user));
            response.put("token", newToken);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired or invalid");
    }

    @PostMapping("send-code")
    public ResponseEntity<RecoveryCodeResponse> sendRecoveryCode(@RequestBody RecoveryCodeRequest email) {
        String code = passwordRecoveryService.sendRecoveryCodeToUser(email.getEmail());
        return ResponseEntity.ok(new RecoveryCodeResponse(code));
    }

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordRecoveryService.resetPassword(request.getEmail(), request.getNewPassword(), request.getCode());
        return ResponseEntity.ok("Password reset successful");
    }
}
