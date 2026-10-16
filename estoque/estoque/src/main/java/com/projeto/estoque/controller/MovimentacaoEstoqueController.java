package com.projeto.estoque.controller;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import com.projeto.estoque.service.MovimentacaoEstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<MovimentacaoEstoque>> movimentacaoPorFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(movimentacaoEstoqueService.movimentacaoPorFuncionario(funcionarioId));
    }

    @GetMapping("/produto/{produtoId}/funcionario/{funcionarioId}")
    public ResponseEntity<List<MovimentacaoEstoque>> movimentacaoPorFuncionarioEProduto(
            @PathVariable Long produtoId, @PathVariable Long funcionarioId) {
        return ResponseEntity.ok(movimentacaoEstoqueService.movimentacaoPorFuncionarioEProduto(produtoId, funcionarioId));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoEstoque>> movimentacaoPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(movimentacaoEstoqueService.movimentacaoPorProduto(produtoId));
    }
}
