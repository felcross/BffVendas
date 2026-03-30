package com.felcross.bff.infrastructure.feign;

import com.felcross.bff.api.dto.in.ProdutoRequest;
import com.felcross.bff.api.dto.out.ProdutoResponse;
import com.felcross.bff.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "produtos", url = "${produtos.url}", configuration = FeignConfig.class)
public interface ProdutosFeignClient {

    @PostMapping("/produtos")
    ProdutoResponse criar(@RequestBody ProdutoRequest req);

    @GetMapping("/produtos/{id}")
    ProdutoResponse buscar(@PathVariable String id);

    @GetMapping("/produtos")
    List<ProdutoResponse> listar();

    @PutMapping("/produtos/{id}")
    ProdutoResponse atualizar(@PathVariable String id, @RequestBody ProdutoRequest req);

    @PatchMapping("/produtos/{id}/estoque/{quantidade}")
    void atualizarEstoque(@PathVariable String id, @PathVariable int quantidade);

    @DeleteMapping("/produtos/{id}")
    void deletar(@PathVariable String id);
}