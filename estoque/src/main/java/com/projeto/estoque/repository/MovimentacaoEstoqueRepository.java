package com.projeto.estoque.repository;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    Page<MovimentacaoEstoque> findAllByEstoqueProdutoId(Long produtoId, Pageable pageable);

    Page<MovimentacaoEstoque> findAllByFuncionarioId(Long funcionarioId, Pageable pageable);

    Page<MovimentacaoEstoque> findAllByEstoqueProdutoIdAndFuncionarioId(
            Long produtoId,
            Long funcionarioId,
            Pageable pageable
    );
}
