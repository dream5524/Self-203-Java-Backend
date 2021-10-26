package com.kms.seft203.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskUpdateByIdDto extends TaskDto {
    @NotNull
    @NumberFormat
    private Integer id;

    public TaskUpdateByIdDto(String description, Boolean isCompletd, Integer id) {
        super(description, isCompletd);
        this.id = id;
    }
}
