package com.felcross.bff.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felcross.bff.api.dto.enumVenda.StatusVenda;
import com.felcross.bff.api.dto.in.ItemVendaRequest;
import com.felcross.bff.api.dto.in.VendaRequest;
import com.felcross.bff.api.dto.out.VendaResponse;
import com.felcross.bff.infrastructure.feign.VendasBffClient;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VendaBffControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VendasBffClient vendaClient;

    @InjectMocks
    private VendaBffController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Deve criar uma venda com sucesso")
    void deveCriarVenda() throws Exception {
        VendaRequest request = VendaRequest.builder()
                .clienteId(1L)
                .itens(List.of(ItemVendaRequest.builder().produtoId("p1").quantidade(2).build()))
                .build();

        VendaResponse response = VendaResponse.builder().id("v1").clienteId(1L).build();

        when(vendaClient.criar(any(VendaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("v1"));
    }

    @Test
    @DisplayName("Deve confirmar uma venda")
    void deveConfirmarVenda() throws Exception {
        VendaResponse response = VendaResponse.builder().id("v1").status(StatusVenda.CONFIRMADA).build();
        when(vendaClient.confirmar("v1")).thenReturn(response);

        mockMvc.perform(patch("/vendas/{id}/confirmar", "v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMADA"));
    }

    @Test
    @DisplayName("Deve listar vendas por cliente")
    void deveListarVendasPorCliente() throws Exception {
        when(vendaClient.listarPorCliente(1L)).thenReturn(List.of(VendaResponse.builder().id("v1").build()));

        mockMvc.perform(get("/vendas/cliente/{clienteId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}