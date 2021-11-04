package com.kms.seft203.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DashboardResponseDto extends DashboardDto {

    public DashboardResponseDto(String title, String layoutType) {
        super(title, layoutType);
    }
}
