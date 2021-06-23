package com.kms.seft203.auth;

import lombok.Data;

@Data
public class User {
    private String id;
    private String email;
    private String password;
    private String fullName;
}
