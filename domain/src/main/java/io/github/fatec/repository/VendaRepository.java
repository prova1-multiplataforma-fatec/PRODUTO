package io.github.fatec.repository;

import java.util.List;
import java.util.Optional;

import io.github.fatec.entity.Venda;

public interface VendaRepository {
    Venda save(Venda venda);

    List<Venda> getAll();

    Optional<Venda> getById(String id);
}
