package com.felcross.bff.api.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemVendaRequest {
    @NotBlank
    private String produtoId;
    @NotNull @Min(1)
    private Integer quantidade;
}
