package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskCreateDto {
    @NotNull(message = "Email cannot be null")
    @ValidEmail
    @Size(min = 12, max = 30, message = "Please email lengthen must be least 12 characters or more")
    private String email;

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 40, message = "description length must be at least 5 characters and less than 40")
    private String description;

    @NotNull(message = "status of Task (isCompleted=true/false) cannot be null")
    private Boolean isCompleted;

    private LocalDate dateCreated;

    public TaskCreateDto(String email, String description, Boolean isCompleted) {
        this.email = email;
        this.description = description;
        this.isCompleted = isCompleted;
    }
}
