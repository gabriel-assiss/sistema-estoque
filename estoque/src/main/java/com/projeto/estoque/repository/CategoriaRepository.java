package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
   Categoria findByNomeContainingIgnoreCase(String nome);
}
