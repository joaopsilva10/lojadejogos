package com.lojadejogos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lojadejogos.models.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {}