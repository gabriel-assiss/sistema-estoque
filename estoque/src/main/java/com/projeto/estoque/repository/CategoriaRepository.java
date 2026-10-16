package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Categoria;
import java.util.List;
import java.util.Optional;

import com.projeto.estoque.enums.StatusCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Page<Categoria> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);
    Page<Categoria> findAllByStatusCategoria(Pageable pageable, StatusCategoria statusCategoria);
    Optional<Categoria> findByNome(String nome);
    Boolean existsByNome(String nome);
}
