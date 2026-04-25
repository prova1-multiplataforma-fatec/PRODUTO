package io.github.fatec.controller.dto.response;

import java.util.List;

public record VendaResponse(
        String id,
        String idCliente,
        List<ItemVendaResponse> itens) {
}
