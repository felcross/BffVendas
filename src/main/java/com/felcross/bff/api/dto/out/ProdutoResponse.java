package com.felcross.bff.api.dto.out;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponse {
    private String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private LocalDateTime createdAt;
}
