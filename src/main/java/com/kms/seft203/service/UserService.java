package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailDuplicatedException;

import java.util.Date;

public interface UserService {
    RegisterResponse save(RegisterRequest userFromReq) throws EmailDuplicatedException;
    void verifyAccount(String verificationCode);
    Boolean checkValidationCode(User user);
    Boolean resetCode(String verificationCode) throws ContactNotFoundException;
}