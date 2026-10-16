package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByMatricula(String matricula);
    Page<Funcionario> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Funcionario findByEmail(String email);
}
