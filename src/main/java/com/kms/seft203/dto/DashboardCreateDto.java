package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCreateDto {
    @ValidEmail
    @Size(min = 12, max = 30, message = "Please email lengthen must be least 12 characters or more ")
    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "title must not be null")
    @Size(min = 5, max = 40)
    private String title;

    @NotNull(message = "layoutType must not be null")
    @Size(min = 5, max = 40)
    private String layoutType;
}
