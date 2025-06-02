package com.lojadejogos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Classe DTO que representa a resposta com o token de acesso JWT.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
}
