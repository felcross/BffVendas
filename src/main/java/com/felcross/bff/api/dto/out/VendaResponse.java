package com.felcross.bff.api.dto.out;

import com.felcross.bff.api.dto.enumVenda.StatusVenda;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaResponse {
    private String id;
    private Long clienteId;
    private String clienteEmail;
    private List<ItemVendaResponse> itens;
    private BigDecimal subtotal;
    private BigDecimal desconto;
    private BigDecimal total;
    private StatusVenda status;
    private LocalDateTime createdAt;
}