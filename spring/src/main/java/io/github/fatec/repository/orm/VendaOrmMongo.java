package io.github.fatec.repository.orm;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "venda")
public record VendaOrmMongo(
        @Id String id,
        String idCliente,
        List<ItemVendaOrmMongo> itens) {
}
