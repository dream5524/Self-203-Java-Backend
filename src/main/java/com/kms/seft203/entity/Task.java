package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import javax.persistence.*;
=======
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
>>>>>>> e75ae5e9b36d8f30804424b3717cb1e24c9a1efd
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Table(name = "task",schema = "public")
public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
=======
@Entity
@Table(name = "task", schema = "public")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
>>>>>>> e75ae5e9b36d8f30804424b3717cb1e24c9a1efd
    private Contact contact;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    public Task(String description, Boolean isCompleted, Contact contact) {
        this.description = description;
        this.isCompleted = isCompleted;
        this.contact = contact;
    }
}
