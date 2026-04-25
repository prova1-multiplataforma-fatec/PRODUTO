package io.github.fatec.controller.adapter;

import java.util.UUID;

import io.github.fatec.controller.dto.request.ProdutoRequest;
import io.github.fatec.controller.dto.response.ProdutoResponse;
import io.github.fatec.entity.Produto;

public class ProdutoControllerAdapter {
    private ProdutoControllerAdapter() {
    }

    public static Produto castRequest(ProdutoRequest request) {
        return new Produto(
                UUID.randomUUID().toString(),
                request.nome(),
                request.preco());
    }

    public static Produto castRequest(String id, ProdutoRequest request) {
        return new Produto(
                id,
                request.nome(),
                request.preco());
    }

    public static ProdutoResponse castResponse(Produto produto) {
        return new ProdutoResponse(
                produto.id(),
                produto.nome(),
                produto.preco());
    }
}
