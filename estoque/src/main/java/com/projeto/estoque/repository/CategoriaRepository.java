package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Categoria;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByNomeContainingIgnoreCase(String nome);
    Optional<Categoria> findByNome(String nome);
}
