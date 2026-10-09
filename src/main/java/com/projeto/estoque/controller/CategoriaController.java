package com.projeto.estoque.controller;

import com.projeto.estoque.dto.categoria.CategoriaDTO;
import com.projeto.estoque.dto.categoria.CategoriaResponseDTO;
import com.projeto.estoque.dto.categoria.CategoriaSaveRequetDTO;
import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaSaveRequetDTO categoriaDTO) {
        return new ResponseEntity<>(categoriaService.salvar(categoriaDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarCategoriasAtivas() {
        return ResponseEntity.ok(categoriaService.buscarCategoriasAtivas());
    }

    @GetMapping("/inativas")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarCategoriasInativas() {
        return ResponseEntity.ok(categoriaService.buscarCategoriasInativas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.atualizar(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        categoriaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CategoriaDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(categoriaService.buscarPorNome(nome));
    }
}
