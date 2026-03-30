package com.felcross.bff.infrastructure.feign;

import com.felcross.bff.api.dto.in.ClienteRequest;
import com.felcross.bff.api.dto.in.LoginRequest;
import com.felcross.bff.api.dto.out.ClienteResponse;
import com.felcross.bff.api.dto.out.LoginResponse;
import com.felcross.bff.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "clientes", url = "${clientes.url}", configuration = FeignConfig.class)
public interface ClientesFeignClient {

    @PostMapping("/clientes")
    ClienteResponse criar(@RequestBody ClienteRequest req);

    @GetMapping("/clientes/{id}")
    ClienteResponse buscar(@PathVariable Long id);

    @GetMapping("/clientes")
    List<ClienteResponse> listar();

    @PutMapping("/clientes/{id}")
    ClienteResponse atualizar(@PathVariable Long id, @RequestBody ClienteRequest req);

    @DeleteMapping("/clientes/{id}")
    void deletar(@PathVariable Long id);

    @PostMapping("/auth/login")
    LoginResponse login(@RequestBody LoginRequest req);
}