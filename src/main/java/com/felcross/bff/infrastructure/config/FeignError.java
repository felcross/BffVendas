package com.felcross.bff.infrastructure.config;


import com.felcross.bff.infrastructure.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {

    private static final String PREFIXO_ERRO = "Erro vindo do Microserviço: ";

    @Override
    public Exception decode(String methodKey, Response response) {
        // 1. Extrai a mensagem real enviada pelo outro serviço
        String mensagemErro = extrairMensagem(response);

        // 2. Log de segurança para você ver no console do BFF o que está acontecendo
        System.err.println("[Feign Error] Status: " + response.status() + " | Método: " + methodKey);
        System.err.println("[Feign Error] Corpo da Resposta: " + mensagemErro);

        return switch (response.status()) {
            case 400 -> new IllegalArgumentException(PREFIXO_ERRO + mensagemErro);
            case 401 -> new UnauthorizedException(PREFIXO_ERRO + "Token Inválido ou Expirado");
            case 403 -> new ForbiddenException(PREFIXO_ERRO + "Acesso Proibido! Verifique permissões ou o Segredo do JWT");
            case 404 -> new ResourceNotFoundException(PREFIXO_ERRO + "Recurso não encontrado no destino");
            case 409 -> new ConflictException(PREFIXO_ERRO + mensagemErro);
            default -> new BusinessException(PREFIXO_ERRO + "Erro inesperado: " + response.status());
        };
    }

    private String extrairMensagem(Response response) {
        try {
            if (response.body() != null) {
                return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            return "Não foi possível ler o corpo do erro";
        }
        return "Corpo vazio";
    }
}