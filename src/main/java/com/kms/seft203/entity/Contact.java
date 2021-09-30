package com.kms.seft203.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "project")
    private String project;

    @NotNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public Contact(String firstName, String lastName, String title, String project) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.project = project;
    }
}