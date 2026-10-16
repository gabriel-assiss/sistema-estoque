package com.projeto.estoque.controller;

import com.projeto.estoque.dto.compra.CompraRequestDTO;
import com.projeto.estoque.dto.compra.CompraResponseDTO;
import com.projeto.estoque.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> salvar(@Valid @RequestBody CompraRequestDTO compraDTO) {
        return new ResponseEntity<>(compraService.salvar(compraDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CompraResponseDTO> cancelar(@PathVariable Long id, @RequestParam Long idFuncionario) {
        return ResponseEntity.ok(compraService.cancelar(id, idFuncionario));
    }

    @GetMapping
    public ResponseEntity<Page<CompraResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(compraService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<Page<CompraResponseDTO>> buscarPorFuncionario(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(compraService.buscarPorFuncionario(id, pageable));
    }

    @GetMapping("/fornecedor/{id}")
    public ResponseEntity<Page<CompraResponseDTO>> buscarPorFornecedor(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(compraService.buscarPorFornecedor(id, pageable));
    }
}
