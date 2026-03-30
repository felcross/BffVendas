package com.felcross.bff.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaRequest {
    @NotNull
    private Long clienteId;
    @NotEmpty
    private List<ItemVendaRequest> itens;
}