package com.kms.seft203.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class ApplicationPropertyConfig {
    @Value("${spring.variable.activation_base_url}")
    private String activationBaseUrl;

    @Value("${spring.variable.code_expiration_minute}")
    private long codeExpirationMinute;
}
