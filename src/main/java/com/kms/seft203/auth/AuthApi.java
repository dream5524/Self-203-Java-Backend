package com.kms.seft203.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "Admin@123".equals(request.getPassword())) {
            LoginResponse loginResponse = new LoginResponse("<token>", "<refresh_token>");

            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
    }
}
