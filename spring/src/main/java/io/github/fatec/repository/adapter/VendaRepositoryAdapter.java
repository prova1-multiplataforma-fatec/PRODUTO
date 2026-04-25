package io.github.fatec.repository.adapter;

import java.util.List;

import io.github.fatec.entity.ItemVenda;
import io.github.fatec.entity.Venda;
import io.github.fatec.repository.orm.ItemVendaOrmMongo;
import io.github.fatec.repository.orm.VendaOrmMongo;

public class VendaRepositoryAdapter {
    private VendaRepositoryAdapter() {
    }

    public static Venda castOrm(VendaOrmMongo orm) {
        List<ItemVenda> itens = orm.itens()
                .stream()
                .map(item -> new ItemVenda(item.codigoProduto(), item.quantidade()))
                .toList();

        return new Venda(orm.id(), orm.idCliente(), itens);
    }

    public static VendaOrmMongo castEntity(Venda entity) {
        List<ItemVendaOrmMongo> itens = entity.itens()
                .stream()
                .map(item -> new ItemVendaOrmMongo(item.codigoProduto(), item.quantidade()))
                .toList();

        return new VendaOrmMongo(entity.id(), entity.idCliente(), itens);
    }
}
