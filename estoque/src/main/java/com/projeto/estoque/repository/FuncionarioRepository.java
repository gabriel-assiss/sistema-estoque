package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByMatricula(String matricula);
    List<Funcionario> findAllByNomeContainingIgnoreCase(String nome);
}
