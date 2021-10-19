package com.kms.seft203.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardRequestDto {
    @NotNull(message = "title must not be null")
    @Size(min = 5, max = 40,message = "Please title lengthen must be least 5 characters or more")
    private String title;

    @NotNull(message = "layoutType must not be null")
    @Size(min = 5, max = 40,message = "Please layoutType lengthen must be least 5 characters or more")
    private String layoutType;
}
