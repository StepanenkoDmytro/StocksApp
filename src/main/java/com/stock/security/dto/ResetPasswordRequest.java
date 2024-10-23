package com.stock.security.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String code;
    private String newPassword;
}
