package com.projeto.estoque.controller;

import com.projeto.estoque.dto.funcionario.FuncionarioDTO;
import com.projeto.estoque.dto.funcionario.FuncionarioResponseDTO;
import com.projeto.estoque.dto.funcionario.FuncionarioSaveRequetDTO;
import com.projeto.estoque.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> salvar(@RequestBody FuncionarioSaveRequetDTO funcionarioDTO) {
        return new ResponseEntity<>(funcionarioService.salvar(funcionarioDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<FuncionarioResponseDTO> inativarFuncionario(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.inativarFuncionario(id));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<FuncionarioResponseDTO> ativarFuncionario(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.ativarFuncionario(id));
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<FuncionarioResponseDTO> atualizaNome(@PathVariable Long id, @RequestParam String nome) {
        return ResponseEntity.ok(funcionarioService.atualizaNome(id, nome));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FuncionarioDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(funcionarioService.buscarPorNome(nome));
    }
}
