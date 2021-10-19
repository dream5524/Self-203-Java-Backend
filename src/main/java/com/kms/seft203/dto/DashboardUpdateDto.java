package com.kms.seft203.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DashboardUpdateDto extends DashboardRequestDto {
    private Integer id;

    public DashboardUpdateDto(String title, String layoutType, Integer id) {
        super(title, layoutType);
        this.id = id;
    }
}
