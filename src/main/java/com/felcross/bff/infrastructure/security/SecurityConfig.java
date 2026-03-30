package com.felcross.bff.infrastructure.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;



@SecurityScheme(name = SecurityConfig.SECURITY_SCHEMA,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SecurityConfig {


    public static final String SECURITY_SCHEMA = "bearerAuth";

}