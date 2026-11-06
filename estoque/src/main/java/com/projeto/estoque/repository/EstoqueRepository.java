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
        SET e.quantidadeEstoque = e.quantidadeEstoque - :quantidadeEstoque
        WHERE e.produto.id = :produtoId
        AND e.quantidadeEstoque >= :quantidadeEstoque
    """)
    int debitarEstoque(
            @Param("produtoId") Long produtoId,
            @Param("quantidadeEstoque") Integer quantidadeEstoque
    );


    @Modifying
    @Query("""
    UPDATE Estoque e
    SET e.quantidadeEstoque = e.quantidadeEstoque + :quantidadeEstoque
    WHERE e.produto.id = :idProduto
""")
    int adicionarEstoque(Long idProduto, Integer quantidadeEstoque);

}