package com.lojadejogos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojadejogos.dto.UsuarioRequest;
import com.lojadejogos.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Endpoint para cadastrar novos usuários no sistema", description = ("Deve-se passar um JSON, como informado na estrutura abaixo."))
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/cadastrar-usuario")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest,
            HttpServletRequest request) {
        if (this.usuarioService.isExisteUsuarioCadastrado(usuarioRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Já existe um usuário cadastrado com esse CPF.");
        }
        this.usuarioService.salvaNovoUsuario(usuarioRequest);
        return ResponseEntity.ok().body("Cadastro realizado!");
    }
}
