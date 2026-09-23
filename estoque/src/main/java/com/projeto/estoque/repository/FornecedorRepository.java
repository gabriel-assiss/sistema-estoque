package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Fornecedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Boolean existsByCnpj(String cnpj);
    List<Fornecedor> findAllByNomeContainingIgnoreCase(String nome);
}
