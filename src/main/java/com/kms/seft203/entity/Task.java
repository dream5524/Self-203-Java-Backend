package com.kms.seft203.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Contact contact;

    @Column(name = "date_created")
    private LocalDate dateCreated;
}
