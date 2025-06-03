package com.lojadejogos.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.lojadejogos.dto.PedidoDTO;
import com.lojadejogos.dto.ProdutoDTO;
import com.lojadejogos.models.Pedido;
import com.lojadejogos.models.Produto;
import com.lojadejogos.repositories.PedidoRepository;
import com.lojadejogos.repositories.ProdutoRepository;
import com.lojadejogos.services.EmbalagemService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmbalagemController {

    private final EmbalagemService embalagemService;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Operation(summary = "Endpoint para cadastrar novos usuários no sistema", description = ("Deve-se passar um JSON, como informado na estrutura abaixo."))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/embalagem")
    public Map<String, List<Map<String, List<Produto>>>> processarPedidos(@RequestBody List<PedidoDTO> pedidos) {
        List<Produto> listaProdutos = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();
        for (PedidoDTO pedidoDto : pedidos) {
            for (ProdutoDTO produtoDto : pedidoDto.getProdutos()) {
                Produto p = new Produto(produtoDto.getNome(), produtoDto.getAltura(), produtoDto.getLargura(),
                        produtoDto.getComprimento());
                listaProdutos.add(produtoRepository.save(p));
            }
            listaPedidos.add(pedidoRepository.save(new Pedido(listaProdutos)));
        }

        return embalagemService.embalarPedidos(listaPedidos);
    }
}