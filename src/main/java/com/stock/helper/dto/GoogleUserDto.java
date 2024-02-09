//package com.stock.helper.dto;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class GoogleUserDto {
//    private String email;
//    private boolean emailVerify;
//
//    public static GoogleUserDto mapGoogleResponse(GoogleIdToken.Payload payload) {
//        return new GoogleUserDto(
//                payload.getEmail(),
//                payload.getEmailVerified()
//        );
//    }
//}
