package com.felcross.bff.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.felcross.bff.api.dto.in.ProdutoRequest;
import com.felcross.bff.api.dto.out.ProdutoResponse;
import com.felcross.bff.infrastructure.feign.ProdutosFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // O segredo está no .servlet.result

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProdutoBffControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProdutosFeignClient produtoClient;

    @InjectMocks
    private ProdutoBffController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    void deveCriarProduto() throws Exception {
        ProdutoRequest request = ProdutoRequest.builder().nome("Teclado").preco(new BigDecimal("200")).estoque(5).build();
        ProdutoResponse response = ProdutoResponse.builder().id("p1").nome("Teclado").build();

        when(produtoClient.criar(any(ProdutoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("p1"));
    }

    @Test
    @DisplayName("Deve atualizar estoque via PATCH")
    void deveAtualizarEstoque() throws Exception {
        mockMvc.perform(patch("/produtos/{id}/estoque/{quantidade}", "p1", 5))
                .andExpect(status().isOk());

        verify(produtoClient).atualizarEstoque("p1", 5);
    }

    @Test
    @DisplayName("Deve buscar produto por ID")
    void deveBuscarProduto() throws Exception {
        ProdutoResponse response = ProdutoResponse.builder().id("p1").nome("Mouse").build();
        when(produtoClient.buscar("p1")).thenReturn(response);

        mockMvc.perform(get("/produtos/{id}", "p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mouse"));
    }
}