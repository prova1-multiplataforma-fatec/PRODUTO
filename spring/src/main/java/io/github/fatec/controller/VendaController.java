package io.github.fatec.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.fatec.controller.adapter.VendaControllerAdapter;
import io.github.fatec.controller.dto.request.VendaRequest;
import io.github.fatec.controller.dto.response.VendaResponse;
import io.github.fatec.entity.Venda;
import io.github.fatec.repository.VendaRepository;

@RestController
public class VendaController {

    private final VendaRepository vendaRepository;

    public VendaController(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @PostMapping("/venda")
    public VendaResponse salvar(@RequestBody VendaRequest request) {
        try {
            Venda venda = VendaControllerAdapter.castRequest(request);
            Venda vendaSalva = vendaRepository.save(venda);
            return VendaControllerAdapter.castResponse(vendaSalva);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/venda")
    public List<VendaResponse> listar() {
        return vendaRepository.getAll()
                .stream()
                .map(VendaControllerAdapter::castResponse)
                .toList();
    }

    @GetMapping("/venda/{id}")
    public VendaResponse buscarPorId(@PathVariable String id) {
        return vendaRepository.getById(id)
                .map(VendaControllerAdapter::castResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda nao encontrada"));
    }
}
