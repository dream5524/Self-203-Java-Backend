package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DashboardCreateDto extends DashboardDto {
    @ValidEmail
    @Size(min = 12, message = "Please email lengthen must be least 12 characters or more ")
    @NotNull(message = "Email must not be null")
    private String email;

    public DashboardCreateDto(String title, String layoutType, String email) {
        super(title, layoutType);
        this.email = email;
    }
}
