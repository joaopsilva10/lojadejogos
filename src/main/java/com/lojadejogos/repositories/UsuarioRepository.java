package com.lojadejogos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lojadejogos.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query(value = "select * from usuarios where username = :username", nativeQuery = true)
    Optional<Usuario> findByUsername(@Param("username") String username);

    @Query(value = "select * from usuarios where username = :username", nativeQuery = true)
    Usuario buscarUsuarioPorUsername(@Param("username") String username);
}
