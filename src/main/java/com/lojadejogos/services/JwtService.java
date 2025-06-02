package com.lojadejogos.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lojadejogos.config.JwtProperties;
import com.lojadejogos.enums.Role;
import com.lojadejogos.models.Token;
import com.lojadejogos.models.Usuario;
import com.lojadejogos.repositories.TokenRepository;
import com.lojadejogos.util.VariaveisPadraoProjeto;

import java.math.BigDecimal;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final TokenRepository tokenRepository;
    private final VariaveisPadraoProjeto variaveisPadrao;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, Role role) {
        Map<String, Object> claims = new HashMap<>();
        if (role == Role.ADMIN)
            claims.put("roles", List.of(Role.ADMIN.name()));
        if (role == Role.USER)
            claims.put("roles", List.of(Role.USER.name()));
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public void saveUserTokenBasico(Usuario usuario, String jwt) {
        Token token = new Token();
        token.setUsuario(usuario);
        token.setToken(jwt);
        //token expiração
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(variaveisPadrao.getExpiresAt() / 1000);
        token.setExpiresAt(expiresAt);
        token.setActive(true);
        token.setRevoked(false);
        token.setCreatedAt(LocalDateTime.now());
        tokenRepository.save(token);
    }

    public void revokeOldTokens(Usuario usuario) {
        List<Token> validTokens = tokenRepository.findByUsuarioAndActiveTrueAndRevokedFalse(usuario);
        validTokens.forEach(t -> {
            t.setRevoked(true);
            t.setActive(false);
        });
        tokenRepository.saveAll(validTokens);
    }

    private BigDecimal parseBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        } catch (Exception e) {
            return null;
        }
    }
}
