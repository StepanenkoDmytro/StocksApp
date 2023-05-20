package com.stock.dto;

import com.stock.model.user.User;
import lombok.Data;

import java.util.Date;

@Data
public class SignUpDto {
    private String email;
    private String username;
    private String password;

    public static User signUpToUser(SignUpDto signUpDTO){
        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());

//        Date date = new Date();
//        user.setCreated(date);
//        user.setUpdated(date);
        return user;
    }
}
