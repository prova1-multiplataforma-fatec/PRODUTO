package io.github.fatec.entity;

import java.util.List;

public record Venda(
        String id,
        String idCliente,
        List<ItemVenda> itens) {
}
