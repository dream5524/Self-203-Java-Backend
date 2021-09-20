package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserSecurity")
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
