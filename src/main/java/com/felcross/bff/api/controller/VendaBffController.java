package com.felcross.bff.api.controller;

import com.felcross.bff.api.dto.in.VendaRequest;
import com.felcross.bff.api.dto.out.VendaResponse;
import com.felcross.bff.infrastructure.feign.VendasBffClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
@Tag(name = "Vendas")
@SecurityRequirement(name = "bearerAuth")
public class VendaBffController {

    private final VendasBffClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar venda")
    public VendaResponse criar(@RequestBody VendaRequest req) { return client.criar(req); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda")
    public VendaResponse buscar(@PathVariable String id) { return client.buscar(id); }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar vendas por cliente")
    public List<VendaResponse> listarPorCliente(@PathVariable Long clienteId) {
        return client.listarPorCliente(clienteId);
    }

    @PatchMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar venda")
    public VendaResponse confirmar(@PathVariable String id) { return client.confirmar(id); }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar venda")
    public VendaResponse cancelar(@PathVariable String id) { return client.cancelar(id); }
}
