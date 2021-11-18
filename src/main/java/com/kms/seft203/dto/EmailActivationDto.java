package com.kms.seft203.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailActivationDto {
    private String email;
    private String activationLink;
}
