package com.kms.seft203.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class ContactRequestDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String title;

    @NotNull
    private String project;

    public ContactRequestDTO(String firstName, String lastName, String title, String project) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.project = project;
    }
}
