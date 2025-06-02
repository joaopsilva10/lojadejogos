package com.lojadejogos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.lojadejogos.enums.Role;
import com.lojadejogos.enums.StatusContaUsuario;
import com.lojadejogos.util.interfaces.ValidCPF;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "O username é obrigatório.")
    @Size(min = 11, max = 11, message = "O username deve conter exatamente 11 caracteres (CPF sem máscara).")
    @Pattern(regexp = "\\d{11}", message = "O username deve conter apenas números.")
    @ValidCPF
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
        message = "A senha deve conter letras maiúsculas, minúsculas, número e símbolo."
    )
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("dataExpiracao")
    private LocalDateTime dataExpiracao;

    @JsonProperty("status")
    private StatusContaUsuario status;
}
