package com.projeto.estoque.dto.produto;

import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.enums.StatusProduto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String unidadeMedida;
    private StatusProduto statusProduto;
    private Long categoriaId;
    private String categoriaNome;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String unidadeMedida, StatusProduto statusProduto, Long categoriaId, String categoriaNome) {
        this.id = id;
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
        this.statusProduto = statusProduto;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
    }

    public ProdutoDTO(Produto produto) {
        if (produto != null) {
            this.id = produto.getId();
            this.nome = produto.getNome();
            this.unidadeMedida = produto.getUnidadeMedida();
            this.statusProduto = produto.getStatusProduto();
            if (produto.getCategoria() != null) {
                this.categoriaId = produto.getCategoria().getId();
                this.categoriaNome = produto.getCategoria().getNome();
            }
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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public StatusProduto getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(StatusProduto statusProduto) {
        this.statusProduto = statusProduto;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
