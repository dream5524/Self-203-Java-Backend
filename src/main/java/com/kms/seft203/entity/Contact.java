package com.kms.seft203.entity;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String title;
    private Integer userId;
    private String project;
    private LocalDate dateCreated;
}
