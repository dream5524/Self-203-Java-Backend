package com.kms.seft203.dto;

import com.kms.seft203.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactResponseDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String project;
}
