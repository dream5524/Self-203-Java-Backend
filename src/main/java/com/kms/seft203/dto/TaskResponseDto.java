package com.kms.seft203.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskResponseDto extends TaskDto {
    private Integer id;
    private LocalDate dateCreated;

    public TaskResponseDto(String description, Boolean isCompleted, LocalDate dateCreated) {
        super(description, isCompleted);
        this.dateCreated = dateCreated;
    }

    public TaskResponseDto(String description, Boolean isCompleted, Integer id) {
        super(description, isCompleted);
        this.id = id;
    }

    public TaskResponseDto(String description, Boolean isCompleted, Integer id, LocalDate dateCreated) {
        super(description, isCompleted);
        this.id = id;
        this.dateCreated = dateCreated;
    }
}
