package com.lojadejogos.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Caixa {
    private String tipo;
    private double altura;
    private double largura;
    private double comprimento;

    public double getVolume() {
        return altura * largura * comprimento;
    }
}