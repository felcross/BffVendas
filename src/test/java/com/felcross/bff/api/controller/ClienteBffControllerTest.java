package com.felcross.bff.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felcross.bff.api.dto.in.ClienteRequest;
import com.felcross.bff.api.dto.out.ClienteResponse;
import com.felcross.bff.infrastructure.feign.ClientesFeignClient;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteBffControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClientesFeignClient clienteClient;

    @InjectMocks
    private ClienteBffController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    @DisplayName("Deve cadastrar um cliente através do BFF")
    void deveCriarCliente() throws Exception {
        ClienteRequest request = ClienteRequest.builder()
                .nome("Felcross")
                .email("teste@teste.com")
                .build();

        ClienteResponse response = ClienteResponse.builder()
                .id(1L)
                .nome("Felcross")
                .build();

        when(clienteClient.criar(any(ClienteRequest.class))).thenReturn(response);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Felcross"));

        verify(clienteClient).criar(any());
    }

    @Test
    @DisplayName("Deve buscar um cliente por ID")
    void deveBuscarCliente() throws Exception {
        Long id = 1L;

        ClienteResponse response = ClienteResponse.builder()
                .id(id)
                .nome("Felcross")
                .build();

        when(clienteClient.buscar(id)).thenReturn(response);

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(clienteClient).buscar(id);
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void deveListarClientes() throws Exception {
        when(clienteClient.listar())
                .thenReturn(List.of(
                        ClienteResponse.builder()
                                .id(1L)
                                .nome("Felcross")
                                .build()
                ));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    void deveDeletarCliente() throws Exception {
        mockMvc.perform(delete("/clientes/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(clienteClient).deletar(1L);
    }
}