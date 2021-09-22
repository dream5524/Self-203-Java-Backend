package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
@Table(name = "RoleSecurity")
public class RoleSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
}
