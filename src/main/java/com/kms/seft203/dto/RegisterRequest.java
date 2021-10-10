package com.kms.seft203.dto;

import com.kms.seft203.validator.ValidEmail;
import com.kms.seft203.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.transform.Source;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @ValidEmail
    @NotNull
    @Size(min = 12, max = 30, message = "Please email lengthen must be least 12 characters or more ")
    private String email;

    @ValidPassword(message = "Password must be includes: 1 upper-case, 1 lower-case, 1 digit, 1 symbol and no space.")
    @NotNull
    @Size(min = 8, max = 20, message = "Please password lengthen must be least 8 characters or more")
    private String password;

    @NotNull
    @Size(min = 2, max = 30, message = "Please full name lengthen must be least 2 characters or more.")
    private String fullName;
}
