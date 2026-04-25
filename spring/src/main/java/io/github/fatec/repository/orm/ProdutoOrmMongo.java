package io.github.fatec.repository.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "produto")
public record ProdutoOrmMongo(
                @Id String id,
                String nome,
                Double preco) {
}