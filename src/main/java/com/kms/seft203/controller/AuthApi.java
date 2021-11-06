package com.kms.seft203.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kms.seft203.dto.LoginRequest;
import com.kms.seft203.dto.LoginResponse;
import com.kms.seft203.dto.LogoutRequest;
import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.VerificationCodeInValidException;
import com.kms.seft203.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthApi {

    private static final Map<String, User> DATA = new HashMap<>();

    @Autowired
    private UserService userService;

    /**
     * @throws EmailDuplicatedException Request format:
     *                                  {
     *                                  "email": "email@gmail.com",
     *                                  "password": "my password",
     *                                  "fullName": "Your Name"
     *                                  }
     *                                  Successful response format:
     *                                  {
     *                                  "email": "email@gmail.com",
     *                                  "password": null,
     *                                  "fullName": "Your Name"
     *                                  }
     *                                  <p>
     *                                  This method is used to receive and handle the user register request.
     * @param: a request
     * @return: a DTO of user if the process succeeds
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) throws EmailDuplicatedException {
        RegisterResponse responseUser = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "Admin@123".equals(request.getPassword())) {
            LoginResponse loginResponse = new LoginResponse(
                    createJwtToken("admin", "Admin User"), "<refresh_token>");
            return ResponseEntity.ok(loginResponse);
        }

        User user = DATA.get(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            LoginResponse loginResponse = new LoginResponse(
                    createJwtToken(request.getUsername(), user.getFullName()), "<refresh_token>");
            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String createJwtToken(String user, String displayName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("this is a secret");
            return JWT.create()
                    .withIssuer("kms")
                    .withClaim("user", user)
                    .withClaim("displayName", displayName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            System.out.println(ex);
            return "";
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("code") String verificationCode) throws VerificationCodeInValidException {
        boolean isVerifiedCode = userService.verifyAccount(verificationCode);
        if (isVerifiedCode) {
            return ResponseEntity.status(200).body("Account was verified successfully !");
        } else {
            return ResponseEntity.status(410).body("Verification failed ! Code expired.");
        }
    }

    @GetMapping("/reset-code")
    public ResponseEntity<String> resetCode(@RequestParam("email") String email) throws EmailNotFoundException {
        userService.resetCode(email);
        return ResponseEntity.ok("Code successfully reset ! Check your email again to confirm your account.");
    }
}