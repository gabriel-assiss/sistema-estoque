package com.projeto.estoque.dto.categoria;

import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.enums.StatusCategoria;

public class CategoriaDTO {

    private Long id;
    private String nome;
    private StatusCategoria statusCategoria;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nome, StatusCategoria statusCategoria) {
        this.id = id;
        this.nome = nome;
        this.statusCategoria = statusCategoria;
    }

    public CategoriaDTO(Categoria categoria) {
        if (categoria != null) {
            this.id = categoria.getId();
            this.nome = categoria.getNome();
            this.statusCategoria = categoria.getStatusCategoria();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusCategoria getStatusCategoria() {
        return statusCategoria;
    }

    public void setStatusCategoria(StatusCategoria statusCategoria) {
        this.statusCategoria = statusCategoria;
    }
}
