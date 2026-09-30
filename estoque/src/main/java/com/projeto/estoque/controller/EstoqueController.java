package com.projeto.estoque.controller;

import com.projeto.estoque.dto.estoque.EstoqueResponseDTO;
import com.projeto.estoque.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponseDTO>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }

    @PutMapping("/adicionar/{idProduto}")
    public ResponseEntity<Void> atualizarQuantidadeMais(@PathVariable Long idProduto, @RequestParam int quantidade) {
        estoqueService.atualizarQuantidadeMais(idProduto, quantidade);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subtrair/{idProduto}")
    public ResponseEntity<Void> atualizarQuantidadeMenos(@PathVariable Long idProduto, @RequestParam int quantidade) {
        estoqueService.atualizarQuantidadeMenos(idProduto, quantidade);
        return ResponseEntity.ok().build();
    }
}
