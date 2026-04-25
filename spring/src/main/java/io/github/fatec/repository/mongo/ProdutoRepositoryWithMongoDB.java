package io.github.fatec.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.github.fatec.repository.orm.ProdutoOrmMongo;

@Repository
public interface ProdutoRepositoryWithMongoDB extends MongoRepository<ProdutoOrmMongo, String> {
}