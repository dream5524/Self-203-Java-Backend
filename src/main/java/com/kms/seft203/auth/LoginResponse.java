package com.kms.seft203.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String refreshToken;
}
