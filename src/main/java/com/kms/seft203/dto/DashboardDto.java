package com.kms.seft203.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public abstract class DashboardDto {
    @NotNull(message = "title must not be null")
    @Size(min = 5, max = 40, message = "Please title lengthen must be least 5 characters or more")
    protected String title;

    @NotNull(message = "layoutType must not be null")
    @Size(min = 5, max = 40, message = "Please layoutType lengthen must be least 5 characters or more")
    protected String layoutType;

    protected DashboardDto() {}

    protected DashboardDto(String title, String layoutType) {
        this.title = title;
        this.layoutType = layoutType;
    }
}
