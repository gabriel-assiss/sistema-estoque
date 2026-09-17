package com.projeto.estoque.service;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import com.projeto.estoque.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public MovimentacaoEstoque salvar(MovimentacaoEstoque movimentacaoEstoque) {
        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    public List<MovimentacaoEstoque> listarTodos() {
        return movimentacaoEstoqueRepository.findAll();
    }

    public Optional<MovimentacaoEstoque> buscarPorId(Long id) {
        return movimentacaoEstoqueRepository.findById(id);
    }

    public MovimentacaoEstoque atualizar(MovimentacaoEstoque movimentacaoEstoque) {
        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    public MovimentacaoEstoque atualizar(Long id, MovimentacaoEstoque movimentacaoEstoque) {
        movimentacaoEstoque.setId(id);
        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    public void deletarPorId(Long id) {
        movimentacaoEstoqueRepository.deleteById(id);
    }
}
