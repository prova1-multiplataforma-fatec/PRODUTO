package io.github.fatec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.github.fatec.entity.Produto;
import io.github.fatec.repository.adapter.ProdutoRepositoryAdapter;
import io.github.fatec.repository.mongo.ProdutoRepositoryWithMongoDB;
import io.github.fatec.repository.orm.ProdutoOrmMongo;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final ProdutoRepositoryWithMongoDB repository;

    public ProdutoRepositoryImpl(ProdutoRepositoryWithMongoDB repository) {
        this.repository = repository;
    }

    @Override
    public Produto save(Produto produto) {
        ProdutoOrmMongo orm = ProdutoRepositoryAdapter.castEntity(produto);
        ProdutoOrmMongo ormSave = repository.save(orm);
        return ProdutoRepositoryAdapter.castOrm(ormSave);
    }

    @Override
    public List<Produto> getAll() {
        return repository.findAll()
                .stream()
                .map(ProdutoRepositoryAdapter::castOrm)
                .toList();
    }

    @Override
    public Optional<Produto> getById(String id) {
        return repository.findById(id)
                .map(ProdutoRepositoryAdapter::castOrm);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
