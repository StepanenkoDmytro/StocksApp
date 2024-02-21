package com.stock.dto.auth;

import com.stock.model.user.User;
import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String password;

    public static User signUpToUser(SignUpDto signUpDTO){
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());

        return user;
    }
}
