package io.github.fatec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.github.fatec.entity.ItemVenda;
import io.github.fatec.entity.Venda;
import io.github.fatec.integracao.ClienteIntegracao;
import feign.FeignException;
import io.github.fatec.repository.adapter.VendaRepositoryAdapter;
import io.github.fatec.repository.mongo.VendaRepositoryWithMongoDB;
import io.github.fatec.repository.orm.VendaOrmMongo;

@Repository
public class VendaRepositoryImpl implements VendaRepository {

    private final VendaRepositoryWithMongoDB repository;
    private final ProdutoRepository produtoRepository;
    private final ClienteIntegracao clienteIntegracao;

    public VendaRepositoryImpl(
            VendaRepositoryWithMongoDB repository,
            ProdutoRepository produtoRepository,
            ClienteIntegracao clienteIntegracao) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.clienteIntegracao = clienteIntegracao;
    }

    @Override
    public Venda save(Venda venda) {
        validarVenda(venda);
        VendaOrmMongo orm = VendaRepositoryAdapter.castEntity(venda);
        VendaOrmMongo ormSave = repository.save(orm);
        return VendaRepositoryAdapter.castOrm(ormSave);
    }

    @Override
    public List<Venda> getAll() {
        return repository.findAll()
                .stream()
                .map(VendaRepositoryAdapter::castOrm)
                .toList();
    }

    @Override
    public Optional<Venda> getById(String id) {
        return repository.findById(id)
                .map(VendaRepositoryAdapter::castOrm);
    }

    private void validarVenda(Venda venda) {
        if (venda.idCliente() == null || venda.idCliente().isBlank()) {
            throw new IllegalArgumentException("Id do cliente e obrigatorio");
        }

        validarCliente(venda.idCliente());

        if (venda.itens() == null || venda.itens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter ao menos um item");
        }

        for (ItemVenda item : venda.itens()) {
            if (item.codigoProduto() == null || item.codigoProduto().isBlank()) {
                throw new IllegalArgumentException("Codigo do produto e obrigatorio");
            }

            if (item.quantidade() == null || item.quantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero");
            }

            produtoRepository.getById(item.codigoProduto())
                    .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado: " + item.codigoProduto()));
        }
    }

    private void validarCliente(String idCliente) {
        try {
            clienteIntegracao.buscarPorId(idCliente);
        } catch (FeignException.NotFound ex) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + idCliente);
        } catch (FeignException ex) {
            throw new IllegalArgumentException("Nao foi possivel validar o cliente no gateway");
        }
    }
}
