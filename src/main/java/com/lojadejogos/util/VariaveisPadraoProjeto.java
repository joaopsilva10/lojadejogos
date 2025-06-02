package com.lojadejogos.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Configuration
public class VariaveisPadraoProjeto {

    @Value("${jwt.accessExpiration}")
    private Long expiresAt;

}
