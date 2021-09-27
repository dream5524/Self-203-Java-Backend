package com.kms.seft203.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name="User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idUser;
    @Column(name = "email", length = 20, nullable = false)
    private String username;
    @Column(name = "password", length = 20, nullable = false)
    private String password;
    @Column(name = "fullname", nullable = true)
    private String fullName;

}