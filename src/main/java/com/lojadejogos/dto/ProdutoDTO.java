package com.lojadejogos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private String nome;
    private double altura;
    private double largura;
    private double comprimento;
}
