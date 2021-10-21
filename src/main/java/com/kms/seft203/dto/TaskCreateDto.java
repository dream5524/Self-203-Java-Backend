package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TaskCreateDto extends TaskDto {
    @NotNull(message = "Email cannot be null")
    @ValidEmail
    @Size(min = 12, max = 30, message = "Please email lengthen must be least 12 characters or more")
    private String email;

    public TaskCreateDto(String description, Boolean isCompleted, String email) {
        super(description, isCompleted);
        this.email = email;
    }
}
