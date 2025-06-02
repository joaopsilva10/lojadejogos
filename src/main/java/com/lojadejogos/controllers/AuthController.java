package com.lojadejogos.controllers;


import com.lojadejogos.dto.AuthRequest;
import com.lojadejogos.dto.AuthResponse;
import com.lojadejogos.models.Usuario;
import com.lojadejogos.services.JwtService;
import com.lojadejogos.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Endpoint realizar login no sistema.", description = ("Deve-se passar um JSON, como informado na estrutura abaixo. Assim ele retornará o token de acesso."))
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        try {
            // 1. Autentica
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 2. Usuário autenticado
            Usuario usuario = usuarioService.buscarUsuarioPorUsername(authRequest.getUsername());
            // 3. Gera e salva token
            String token = jwtService.generateToken(usuario.getUsername(), usuario.getRole());
            jwtService.revokeOldTokens(usuario);
            jwtService.saveUserTokenBasico(usuario, token);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            // Credenciais inválidas: login falhou
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login inválido! Use credenciais válidas.");
        }
    }
}