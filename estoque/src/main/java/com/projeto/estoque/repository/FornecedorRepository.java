package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    Boolean existsByCnpj(String cnpj);
    Page<Fornecedor> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
