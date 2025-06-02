package com.lojadejogos.models;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lojadejogos.enums.Role;
import com.lojadejogos.enums.StatusContaUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa um usuário no sistema.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "data_incl")
    @CreationTimestamp
    private LocalDateTime dataIncl;

    @Column(name = "data_ult_altr")
    @UpdateTimestamp
    private LocalDateTime dataUltAltr;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(name = "ultima_troca_senha")
    private LocalDateTime ultimaTrocaSenha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusContaUsuario status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDateTime.now().isBefore(this.dataExpiracao);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.status.equals(StatusContaUsuario.BLOQUEADO);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.ultimaTrocaSenha.isAfter(LocalDateTime.now().minusDays(180));
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(StatusContaUsuario.ATIVO);
    }
}
