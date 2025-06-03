package com.lojadejogos.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double altura;
    private double largura;
    private double comprimento;

    public Produto(String nome, double altura, double largura, double comprimento) {
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public double getVolume() {
        return altura * largura * comprimento;
    }

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}
