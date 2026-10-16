package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Compra;
import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    Page<Compra> findByFuncionario(Funcionario funcionario, Pageable pageable);

    Page<Compra> findByFornecedor(Fornecedor fornecedor, Pageable pageable);
}
