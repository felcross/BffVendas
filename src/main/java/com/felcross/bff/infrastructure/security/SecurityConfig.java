package com.felcross.bff.infrastructure.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration // CRITICAL: Indica que é uma classe de configuração
@EnableWebSecurity // CRITICAL: Ativa a segurança do Spring
@SecurityScheme(
        name = SecurityConfig.SECURITY_SCHEMA,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SecurityConfig {

    public static final String SECURITY_SCHEMA = "bearerAuth";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Vincula o CORS definido no bean abaixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Desabilita CSRF (necessário para APIs REST)
                .csrf(csrf -> csrf.disable())

                // 3. Permite todas as requisições (BFF -> Microserviços)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origens permitidas (Seu Front-end e o próprio BFF se necessário)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "http://127.0.0.1:5173"
        ));

        // Permite TODOS os métodos e cabeçalhos para evitar erros de Preflight
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Usar "*" aqui ajuda muito em desenvolvimento
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}