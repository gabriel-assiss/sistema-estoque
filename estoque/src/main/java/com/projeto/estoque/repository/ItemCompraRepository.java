package com.projeto.estoque.repository;

import com.projeto.estoque.entity.Compra;
import com.projeto.estoque.entity.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    List<ItemCompra> findByCompra(Compra compra);
}
