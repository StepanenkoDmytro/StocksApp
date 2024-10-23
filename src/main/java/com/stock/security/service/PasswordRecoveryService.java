package com.stock.security.service;

import com.stock.mail.MailService;
import com.stock.model.user.User;
import com.stock.model.user.service.UserService;
import com.stock.exceptions.AuthSourceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordRecoveryService {
    private final UserService userService;
    private final MailService mailService;

    public PasswordRecoveryService(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    public String sendRecoveryCodeToUser(String email) {
        //TODO: rewrite when create ExceptionHandler and change return null
        Optional<User> optionalUserByEmail = userService.getUserByEmail(email);
        if(optionalUserByEmail.isPresent()) {
            User userByEmail = optionalUserByEmail.get();
            if (userByEmail.getAuthSource() != null) {
                throw new AuthSourceException("Password reset is unavailable for users authenticated via external OAuth providers");
            }

            String recoveryCode = generateRecoveryCode();
            LocalDateTime recoveryTime = LocalDateTime.now().plusMinutes(5);

            userByEmail.setRecoveryCode(recoveryCode);
            userByEmail.setRecoveryCodeExpiration(recoveryTime);

            userService.saveUser(userByEmail);
            mailService.sendRecoveryCode(email, recoveryCode);
            return recoveryCode;
        }
        return null;
    }

    public void resetPassword(String email, String newPassword, String recoveryCode) {
        Optional<User> optionalUserByEmail = userService.getUserByEmail(email);

        if(optionalUserByEmail.isPresent()) {
            User userByEmail = optionalUserByEmail.get();
            if(validateRecoveryCode(userByEmail, recoveryCode)) {
                userByEmail.setPassword(userService.passwordEncoder().encode(newPassword));
                userByEmail.setRecoveryCode(null);
                userByEmail.setRecoveryCodeExpiration(null);
                userService.saveUser(userByEmail);
            }
        }
    }

    private String generateRecoveryCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public boolean validateRecoveryCode(User user, String code) {
        if (user.getRecoveryCode().equals(code)) {
            return user.getRecoveryCodeExpiration().isAfter(LocalDateTime.now());
        }
        return false;
    }
}
