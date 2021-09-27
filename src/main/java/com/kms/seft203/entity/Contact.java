package com.kms.seft203.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContact;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "title")
    private String title;
//
//    @NotNull
//    @OneToOne
//    @JoinColumn(name="idUser")
//    private User user;

    @NotNull
    @Column(name = "project")
    private String project;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

}