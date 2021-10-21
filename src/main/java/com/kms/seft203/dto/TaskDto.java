package com.kms.seft203.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public abstract class TaskDto {
    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 40, message = "description length must be at least 5 characters and less than 40")
    protected String description;

    @NotNull(message = "status of Task (isCompleted=true/false) cannot be null")
    protected Boolean isCompleted;

    public TaskDto(String description, Boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }
}
