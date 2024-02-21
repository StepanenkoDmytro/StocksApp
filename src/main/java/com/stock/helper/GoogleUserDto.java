package com.stock.helper;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.stock.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserDto {
    private String email;
    private boolean emailVerify;

    public static GoogleUserDto mapGoogleResponse(GoogleIdToken.Payload payload) {
        return new GoogleUserDto(
                payload.getEmail(),
                payload.getEmailVerified()
        );
    }

    public static User signUpGoogleToUser(GoogleUserDto signUpDTO) {
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setPassword("");

        return user;
    }
}
