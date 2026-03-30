package com.felcross.bff.api.dto.out;



import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private EnderecoViaCepResponse endereco;
    private LocalDateTime createdAt;


}
