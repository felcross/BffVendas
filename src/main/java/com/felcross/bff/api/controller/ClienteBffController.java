package com.felcross.bff.api.controller;

import com.felcross.bff.api.dto.in.ClienteRequest;
import com.felcross.bff.api.dto.out.ClienteResponse;
import com.felcross.bff.infrastructure.feign.ClientesFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteBffController {

    private final ClientesFeignClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar cliente")
    public ClienteResponse criar(@Valid @RequestBody ClienteRequest req) { return client.criar(req); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente")
    public ClienteResponse buscar(@PathVariable Long id) { return client.buscar(id); }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public List<ClienteResponse> listar() { return client.listar(); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    public ClienteResponse atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest req) {
        return client.atualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar cliente")
    public void deletar(@PathVariable Long id) { client.deletar(id); }
}