package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Compra;
import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    Optional<List<Compra>> findByFuncionario(Funcionario funcionario);

    Optional<List<Compra>>findByFornecedor(Fornecedor fornecedor);
}
