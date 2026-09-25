package com.projeto.estoque.repository;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findAllByEstoqueProdutoId(Long produtoId);

    List<MovimentacaoEstoque> findAllByFuncionarioId(Long funcionarioId);

    List<MovimentacaoEstoque> findAllByEstoqueProdutoIdAndFuncionarioId(
            Long produtoId,
            Long funcionarioId
    );
}
