package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;

public interface UserService {
    RegisterRequest save(RegisterRequest userFromReq);

}
