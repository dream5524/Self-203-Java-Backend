package com.kms.seft203.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @Column(name = "first_name")
    private String firstName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "project")
    private String project;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public Contact(String firstName, String lastName, User user, String title, String project) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
        this.title = title;
        this.project = project;
    }
}