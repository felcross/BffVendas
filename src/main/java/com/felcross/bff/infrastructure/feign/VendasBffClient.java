package com.felcross.bff.infrastructure.feign;

import com.felcross.bff.api.dto.in.VendaRequest;
import com.felcross.bff.api.dto.out.VendaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "vendas", url = "${vendas.url}")
public interface VendasBffClient {

    @PostMapping("/vendas")
    VendaResponse criar(@RequestBody VendaRequest req);

    @GetMapping("/vendas/{id}")
    VendaResponse buscar(@PathVariable String id);

    @GetMapping("/vendas/cliente/{clienteId}")
    List<VendaResponse> listarPorCliente(@PathVariable Long clienteId);

    @PatchMapping("/vendas/{id}/confirmar")
    VendaResponse confirmar(@PathVariable String id);

    @PatchMapping("/vendas/{id}/cancelar")
    VendaResponse cancelar(@PathVariable String id);
}
