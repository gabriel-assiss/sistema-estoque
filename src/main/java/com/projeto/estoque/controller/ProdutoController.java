package com.projeto.estoque.controller;

import com.projeto.estoque.dto.produto.ProdutoDTO;
import com.projeto.estoque.dto.produto.ProdutoResponseDTO;
import com.projeto.estoque.dto.produto.ProdutoSaveRequetDTO;
import com.projeto.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Validated
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(
            @Valid @RequestBody ProdutoSaveRequetDTO produtoDTO,
            @RequestParam @Positive(message = "Quantidade em estoque deve ser positiva") int quantidade) {
        return new ResponseEntity<>(produtoService.salvar(produtoDTO, quantidade), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoriaNome}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(@PathVariable String categoriaNome) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoriaNome));
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<ProdutoResponseDTO> atualizarParaInativo(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.atualizarParaInativo(id));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<ProdutoResponseDTO> atualizarParaAtivo(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.atualizarParaAtivo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }
}
