package com.kms.seft203.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public abstract class TaskDto {
    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 40, message = "description length must be at least 5 characters and less than 40")
    protected String description;

    @NotNull(message = "status of Task (isCompleted=true/false) cannot be null")
    protected Boolean isCompleted;

    protected TaskDto() {}

    protected TaskDto(String description, Boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }
}
