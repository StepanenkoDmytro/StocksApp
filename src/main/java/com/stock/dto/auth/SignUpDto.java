package com.stock.dto.auth;

//import com.stock.helper.dto.GoogleUserDto;
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

//    public static User signUpGoogleToUser(GoogleUserDto signUpDTO){
//        User user = new User();
//        user.setEmail(signUpDTO.getEmail());
//        user.setPassword("");
//
//        return user;
//    }
}
