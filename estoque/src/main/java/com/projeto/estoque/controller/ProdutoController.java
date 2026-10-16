package com.projeto.estoque.controller;

import com.projeto.estoque.dto.produto.ProdutoDTO;
import com.projeto.estoque.dto.produto.ProdutoResponseDTO;
import com.projeto.estoque.dto.produto.ProdutoSaveRequetDTO;
import com.projeto.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<ProdutoResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoriaNome}")
    public ResponseEntity<Page<ProdutoResponseDTO>> buscarPorCategoria(@PathVariable String categoriaNome, Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoriaNome, pageable));
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
    public ResponseEntity<Page<ProdutoDTO>> buscarPorNome(@PathVariable String nome, Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome, pageable));
    }
}
