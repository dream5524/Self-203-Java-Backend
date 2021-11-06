package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.VerificationCodeInValidException;


public interface UserService {
    RegisterResponse save(RegisterRequest userFromReq) throws EmailDuplicatedException;
    Boolean verifyAccount(String verificationCode) throws VerificationCodeInValidException;
    Boolean checkValidationCode(User user);
    User resetCode(String email) throws EmailNotFoundException;
}