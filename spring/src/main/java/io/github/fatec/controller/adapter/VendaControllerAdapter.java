package io.github.fatec.controller.adapter;

import java.util.List;
import java.util.UUID;

import io.github.fatec.controller.dto.request.VendaRequest;
import io.github.fatec.controller.dto.response.ItemVendaResponse;
import io.github.fatec.controller.dto.response.VendaResponse;
import io.github.fatec.entity.ItemVenda;
import io.github.fatec.entity.Venda;

public class VendaControllerAdapter {
    private VendaControllerAdapter() {
    }

    public static Venda castRequest(VendaRequest request) {
        List<ItemVenda> itens = request.itens()
                .stream()
                .map(item -> new ItemVenda(item.codigoProduto(), item.quantidade()))
                .toList();

        return new Venda(UUID.randomUUID().toString(), request.idCliente(), itens);
    }

    public static VendaResponse castResponse(Venda venda) {
        List<ItemVendaResponse> itens = venda.itens()
                .stream()
                .map(item -> new ItemVendaResponse(item.codigoProduto(), item.quantidade()))
                .toList();

        return new VendaResponse(venda.id(), venda.idCliente(), itens);
    }
}
