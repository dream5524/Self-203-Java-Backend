package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactRequestDto {

    @ValidEmail
    @NotNull
    @Size(min = 12, max = 30, message = "Please email lengthen must be least 12 characters or more ")
    private String email;

    @NotNull
    @Size(min = 2, max = 20, message = "Please first name lengthen must be least 2 to 20 characters ")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20, message = "Please last name lengthen must be least 1 to 20 characters")
    private String lastName;

    @NotNull
    @Size(min = 2, max = 30, message = "Please title lengthen must be least 2 to 20 characters")
    private String title;

    @NotNull
    @Size(min = 2, max = 80, message = "Please project lengthen must be least 2 to 80 characters")
    private String project;
}
