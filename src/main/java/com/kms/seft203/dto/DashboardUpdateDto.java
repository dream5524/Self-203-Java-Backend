package com.kms.seft203.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DashboardUpdateDto extends DashboardDto {
    @NotNull
    @Min(1)
    private Integer id;

    public DashboardUpdateDto(String title, String layoutType, Integer id) {
        super(title, layoutType);
        this.id = id;
    }
}
