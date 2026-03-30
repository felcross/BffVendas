package com.felcross.bff.api.controller;

import com.felcross.bff.api.dto.in.LoginRequest;
import com.felcross.bff.api.dto.out.LoginResponse;
import com.felcross.bff.infrastructure.feign.ClientesFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthBffController {

    private final ClientesFeignClient clientesClient;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return clientesClient.login(req);
    }
}
