package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This entity + RoleSecurity is used particularly for Spring Boot security customization
 * Example: only ADMIN system can call the api get /users.
 *          {"username": "KMS", roles: ["ADMIN", "USER"]} --> login successful
 *          {"username": "TMA", roles: ["MANAGER"]} --> login failed
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_security")
public class UserSecurity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_security_role_security",
        joinColumns = @JoinColumn(name = "user_security_id"),
        inverseJoinColumns = @JoinColumn(name = "role_security_id"))
    private Collection<RoleSecurity> roleSecurities = new ArrayList<>();
}
