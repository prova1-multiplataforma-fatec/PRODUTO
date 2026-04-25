package io.github.fatec.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.fatec.controller.adapter.ProdutoControllerAdapter;
import io.github.fatec.controller.dto.request.ProdutoRequest;
import io.github.fatec.controller.dto.response.ProdutoResponse;
import io.github.fatec.entity.Produto;
import io.github.fatec.repository.ProdutoRepository;

@RestController
public class ProdutoController {

    public final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/produto")
    public ProdutoResponse salvar(@RequestBody ProdutoRequest request) {
        Produto produto = ProdutoControllerAdapter.castRequest(request);
        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoControllerAdapter.castResponse(produtoSalvo);
    }

    @GetMapping("/produto")
    public List<ProdutoResponse> listar() {
        return produtoRepository.getAll()
                .stream()
                .map(ProdutoControllerAdapter::castResponse)
                .toList();
    }

    @GetMapping("/produto/{id}")
    public ProdutoResponse buscarPorId(@PathVariable String id) {
        return produtoRepository.getById(id)
                .map(ProdutoControllerAdapter::castResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @PutMapping("/produto/{id}")
    public ProdutoResponse atualizar(@PathVariable String id, @RequestBody ProdutoRequest request) {
        produtoRepository.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        Produto produto = ProdutoControllerAdapter.castRequest(id, request);
        Produto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoControllerAdapter.castResponse(produtoAtualizado);
    }

    @DeleteMapping("/produto/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id) {
        produtoRepository.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        produtoRepository.deleteById(id);
    }

}
