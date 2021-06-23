package com.kms.seft203.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        // TODO: remove user's password
        return new User();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return new LoginResponse();
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
    }
}
