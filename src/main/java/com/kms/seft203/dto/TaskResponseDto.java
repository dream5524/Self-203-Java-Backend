package com.kms.seft203.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponseDto extends TaskDto {
    private LocalDate dateCreated;

    public TaskResponseDto(String description, Boolean isCompleted) {
        super(description, isCompleted);
    }

    public TaskResponseDto(String description, Boolean isCompleted, LocalDate dateCreated) {
        super(description, isCompleted);
        this.dateCreated = dateCreated;
    }
}
