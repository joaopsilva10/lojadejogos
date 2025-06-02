package com.lojadejogos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lojadejogos.models.Token;
import com.lojadejogos.models.Usuario;

public interface TokenRepository extends CrudRepository<Token, Long> {
    List<Token> findByUsuarioAndActiveTrueAndRevokedFalse(Usuario usuario);

    Optional<Token> findByToken(String token);
}
