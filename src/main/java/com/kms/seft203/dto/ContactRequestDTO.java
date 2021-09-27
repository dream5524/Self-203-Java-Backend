package com.kms.seft203.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
public class ContactRequestDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String title;

    @NotNull
//    private User user;

    @NotNull
    private String project;

    @JsonIgnore
    private final LocalDateTime dateCreated = LocalDateTime.now();

}
