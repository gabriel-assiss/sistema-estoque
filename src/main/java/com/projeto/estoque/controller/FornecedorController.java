package com.projeto.estoque.controller;

import com.projeto.estoque.dto.fornecedor.FornecedorDTO;
import com.projeto.estoque.dto.fornecedor.FornecedorResponseDTO;
import com.projeto.estoque.dto.fornecedor.FornecedorSaveRequetDTO;
import com.projeto.estoque.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> salvar(@Valid @RequestBody FornecedorSaveRequetDTO fornecedorDTO) {
        return new ResponseEntity<>(fornecedorService.salvar(fornecedorDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<FornecedorResponseDTO> inativarFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.inativarFornecedor(id));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<FornecedorResponseDTO> ativarFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.ativarFornecedor(id));
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<FornecedorResponseDTO> atualizaNome(@PathVariable Long id, @RequestParam String nome) {
        return ResponseEntity.ok(fornecedorService.atualizaNome(id, nome));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FornecedorDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(fornecedorService.buscarPorNome(nome));
    }
}
