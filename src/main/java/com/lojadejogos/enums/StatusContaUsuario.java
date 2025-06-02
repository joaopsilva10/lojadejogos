package com.lojadejogos.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusContaUsuario {
    ATIVO, INATIVO, BLOQUEADO, ACESSO_EXPIRADO;

    @JsonCreator
    public static StatusContaUsuario from(String value) {
        try {
            return StatusContaUsuario.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("status inválido: \"" + value + "\".");
        }
    }
}