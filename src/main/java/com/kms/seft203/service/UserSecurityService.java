package com.kms.seft203.service;

import com.kms.seft203.entity.RoleSecurity;
import com.kms.seft203.entity.UserSecurity;

import java.util.List;

public interface UserSecurityService {
    UserSecurity saveUser(UserSecurity userSecurity);
    RoleSecurity saveRole(RoleSecurity roleSecurity);
    void addRoleToUser(String username, String roleName);
    UserSecurity getUser(String username);
    List<UserSecurity> getUsers();
}
