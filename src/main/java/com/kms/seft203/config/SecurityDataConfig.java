package com.kms.seft203.config;

import com.kms.seft203.config.SecurityConfig;
import com.kms.seft203.entity.RoleSecurity;
import com.kms.seft203.entity.UserSecurity;
import com.kms.seft203.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * This class is implemented to initialize the data to customize the Spring Security,
 * including the UserSecurity & RoleSecurity
 */
@Component
@RequiredArgsConstructor @Slf4j
public class SecurityDataConfig {
    @Autowired
    private SecurityService securityService;

    public void initSecurityData() {
        /**
         * try inserting role and user security into database if they do not exist.
         * {
         *      name: "loc",
         *      username: "loc",
         *      password: "1",
         *      roles: ["ROLE_ADMIN"]
         * }
         * {
         *      name: "mo",
         *      username: "mo",
         *      password: "1",
         *      roles: ["ROLE_USER"]}
         * }
         */
        try {
            UserSecurity userLoc = securityService.getUser("loc");
            RoleSecurity roleAdmin = securityService.getRole(SecurityConfig.ROLE_ADMIN);
            UserSecurity userMo = securityService.getUser("mo");
            RoleSecurity roleUser = securityService.getRole(SecurityConfig.ROLE_USER);

            if (userLoc == null) {
                securityService.saveUser(new UserSecurity(null, "loc", "loc", "1", new ArrayList<>()));
            }
            if (userMo == null) {
                securityService.saveUser(new UserSecurity(null, "mo", "mo", "1", new ArrayList<>()));
            }
            if (roleAdmin == null) {
                securityService.saveRole(new RoleSecurity(null, SecurityConfig.ROLE_ADMIN));
                securityService.addRoleToUser("loc", SecurityConfig.ROLE_ADMIN);
            }
            if (roleUser == null) {
                securityService.saveRole(new RoleSecurity(null, SecurityConfig.ROLE_USER));
                securityService.addRoleToUser("mo", SecurityConfig.ROLE_USER);
            }
        }
        catch (Exception e) {

            log.error("Error when init the data for security configuration: {}", e.getMessage());
        }

    }
}
