package com.felcross.bff.api.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRequest {
    private String nome;
    private String email;
    private String cpf;
    private String password;
    private String telefone;
    private String cep;
}
