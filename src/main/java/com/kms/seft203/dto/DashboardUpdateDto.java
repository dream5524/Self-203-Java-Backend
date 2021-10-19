package com.kms.seft203.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class DashboardUpdateDto extends DashboardRequestDto {
    @NotNull
    @Min(1)
    private Integer id;

    public DashboardUpdateDto(String title, String layoutType, Integer id) {
        super(title, layoutType);
        this.id = id;
    }
}
