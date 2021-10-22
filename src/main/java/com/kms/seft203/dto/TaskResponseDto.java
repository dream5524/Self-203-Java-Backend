package com.kms.seft203.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskResponseDto extends TaskDto {
    private LocalDate dateCreated;

    public TaskResponseDto(String description, Boolean isCompleted, LocalDate dateCreated) {
        super(description, isCompleted);
        this.dateCreated = dateCreated;
    }
}
