package io.github.fatec.repository;

import io.github.fatec.entity.Produto;

import java.util.List;
import java.util.Optional;



public interface ProdutoRepository {
    Produto save(Produto produto);

    List<Produto> getAll();

    Optional<Produto> getById(String id);

    void deleteById(String id);
}