package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.exception.EmailDuplicatedException;

public interface UserService {
    RegisterResponse save(RegisterRequest userFromReq) throws EmailDuplicatedException;
}