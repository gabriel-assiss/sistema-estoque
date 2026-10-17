package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    @Modifying
    @Query("""
        UPDATE Estoque e
        SET e.quantidade = e.quantidade - :quantidade
        WHERE e.produto.id = :produtoId
        AND e.quantidade >= :quantidade
    """)
    int debitarEstoque(
            @Param("produtoId") Long produtoId,
            @Param("quantidade") Integer quantidade
    );


    @Modifying
    @Query("""
    UPDATE Estoque e
    SET e.quantidadeEstoque = e.quantidadeEstoque + :quantidade
    WHERE e.produto.id = :idProduto
""")
    int adicionarEstoque(Long idProduto, Integer quantidade);

}