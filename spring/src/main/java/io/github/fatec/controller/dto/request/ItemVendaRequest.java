package io.github.fatec.controller.dto.request;

public record ItemVendaRequest(
        String codigoProduto,
        Integer quantidade) {
}
