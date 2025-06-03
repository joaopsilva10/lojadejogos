package com.lojadejogos.services;

import org.springframework.stereotype.Service;

import com.lojadejogos.models.Caixa;
import com.lojadejogos.models.Pedido;
import com.lojadejogos.models.Produto;

import java.util.*;

@Service
public class EmbalagemService {

    private final List<Caixa> caixasDisponiveis = List.of(
            new Caixa("Caixa 1", 30, 40, 80),
            new Caixa("Caixa 2", 80, 50, 40),
            new Caixa("Caixa 3", 50, 80, 60));

    public Map<String, List<Map<String, List<Produto>>>> embalarPedidos(List<Pedido> pedidos) {
        Map<String, List<Map<String, List<Produto>>>> resultado = new HashMap<>();

        for (Pedido pedido : pedidos) {
            List<Produto> itens = new ArrayList<>(pedido.getProduto());
            itens.sort(Comparator.comparingDouble(Produto::getVolume).reversed());

            List<Map<String, List<Produto>>> caixasUsadas = new ArrayList<>();

            while (!itens.isEmpty()) {
                boolean itemAdicionado = false;

                for (Caixa caixa : caixasDisponiveis) {
                    List<Produto> itensNaCaixa = new ArrayList<>();
                    double volumeRestante = caixa.getVolume();

                    Iterator<Produto> iterator = itens.iterator();
                    while (iterator.hasNext()) {
                        Produto item = iterator.next();
                        if (item.getVolume() <= volumeRestante) {
                            itensNaCaixa.add(item);
                            volumeRestante -= item.getVolume();
                            iterator.remove();
                            itemAdicionado = true;
                        }
                    }

                    if (!itensNaCaixa.isEmpty()) {
                        Map<String, List<Produto>> itemMap = new HashMap<>();
                        itemMap.put(caixa.getTipo(), itensNaCaixa);
                        caixasUsadas.add(itemMap);
                        break; // tenta uma caixa por vez
                    }
                }

                if (!itemAdicionado)
                    break; // Evita loop infinito se não couber em nenhuma caixa
            }

            resultado.put("PEDIDO_ID_" + pedido.getId(), caixasUsadas);
        }

        return resultado;
    }

}