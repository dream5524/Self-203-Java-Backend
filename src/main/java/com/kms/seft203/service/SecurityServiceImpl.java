package com.kms.seft203.service;

import com.kms.seft203.entity.RoleSecurity;
import com.kms.seft203.entity.UserSecurity;
import com.kms.seft203.repository.RoleSecurityRepo;
import com.kms.seft203.repository.UserSecurityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class SecurityServiceImpl implements SecurityService, UserDetailsService {
    @Autowired
    private UserSecurityRepo userSecurityRepo;
    @Autowired
    private RoleSecurityRepo roleSecurityRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = userSecurityRepo.findByUsername(username);
        if (userSecurity == null) {
            log.error("User {} not found in the database", username);
            throw new UsernameNotFoundException("user not found in the database");
        } else {
            log.info("user {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userSecurity.getRoleSecurities().forEach(roleSecurity -> {
            authorities.add(new SimpleGrantedAuthority(roleSecurity.getName()));
        });

        return new org.springframework.security.core.
                userdetails.User(userSecurity.getUsername(), userSecurity.getPassword(), authorities);
    }

    @Override
    public UserSecurity saveUser(UserSecurity userSecurity) {
        log.info("Saving new user {} to the database...", userSecurity.getUsername());
        userSecurity.setPassword(passwordEncoder.encode(userSecurity.getPassword()));
        return userSecurityRepo.save(userSecurity);
    }

    @Override
    public RoleSecurity saveRole(RoleSecurity roleSecurity) {
        log.info("Saving new role {} to the database...", roleSecurity.getName());
        return roleSecurityRepo.save(roleSecurity);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to the user {}...", roleName, username);
        UserSecurity userSecurity = userSecurityRepo.findByUsername(username);
        RoleSecurity roleSecurity = roleSecurityRepo.findByName(roleName);
        userSecurity.getRoleSecurities().add(roleSecurity);
    }

    @Override
    public UserSecurity getUser(String username) {
        log.info("Fetching user {}...", username);
        return userSecurityRepo.findByUsername(username);
    }

    @Override
    public RoleSecurity getRole(String roleName) {
        log.info("Fetching role {}...", roleName);
        return roleSecurityRepo.findByName(roleName);
    }

    @Override
    public List<UserSecurity> getUsers() {
        log.info("Fetching all users...");
        return userSecurityRepo.findAll();
    }
}
