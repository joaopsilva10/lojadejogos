package com.lojadejogos.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lojadejogos.dto.UsuarioRequest;
import com.lojadejogos.models.Usuario;
import com.lojadejogos.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public boolean isExisteUsuarioCadastrado(String username) {
        return this.usuarioRepository.findByUsername(username).isPresent();
    }

    public Usuario buscarUsuarioPorUsername(String username) {
        return this.usuarioRepository.buscarUsuarioPorUsername(username);
    }

    public Usuario salvaNovoUsuario(UsuarioRequest usuarioRequest) {
        try {
            var novoUsuario = new Usuario();
            novoUsuario.setUsername(usuarioRequest.getUsername());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            novoUsuario.setPassword(encoder.encode(usuarioRequest.getPassword()));
            novoUsuario.setRole(usuarioRequest.getRole());
            novoUsuario.setDataExpiracao(usuarioRequest.getDataExpiracao());
            novoUsuario.setStatus(usuarioRequest.getStatus());
            return this.usuarioRepository.save(novoUsuario);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Erro ao salvar usuário: dados duplicados ou inválidos.");
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao salvar o usuário.");
        }
    }
}
