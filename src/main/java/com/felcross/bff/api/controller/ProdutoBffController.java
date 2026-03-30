package com.felcross.bff.api.controller;

import com.felcross.bff.api.dto.in.ProdutoRequest;
import com.felcross.bff.api.dto.out.ProdutoResponse;
import com.felcross.bff.infrastructure.feign.ProdutosFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoBffController {

    private final ProdutosFeignClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar produto")
    public ProdutoResponse criar(@Valid @RequestBody ProdutoRequest req) { return client.criar(req); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto")
    public ProdutoResponse buscar(@PathVariable String id) { return client.buscar(id); }

    @GetMapping
    @Operation(summary = "Listar produtos")
    public List<ProdutoResponse> listar() { return client.listar(); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public ProdutoResponse atualizar(@PathVariable String id, @Valid @RequestBody ProdutoRequest req) {
        return client.atualizar(id, req);
    }

    @PatchMapping("/{id}/estoque/{quantidade}")
    @Operation(summary = "Decrementar estoque")
    public void atualizarEstoque(@PathVariable String id, @PathVariable int quantidade) {
        client.atualizarEstoque(id, quantidade);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar produto")
    public void deletar(@PathVariable String id) { client.deletar(id); }
}
