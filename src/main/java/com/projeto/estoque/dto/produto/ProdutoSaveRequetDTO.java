package com.projeto.estoque.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProdutoSaveRequetDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Unidade de medida não pode ser vazia")
    private  String UnidadeMedida;

    @Positive(message = "Quantidade em estoque deve ser positiva")
    private int quantidade;

    @Positive(message = "Categoria deve ser informada")
    private Long categoriaId;

    public ProdutoSaveRequetDTO(String nome, String unidadeMedida, int quantidade, Long categoriaId) {
        this.nome = nome;
        UnidadeMedida = unidadeMedida;
        this.quantidade = quantidade;
        this.categoriaId = categoriaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidadeMedida() {
        return UnidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        UnidadeMedida = unidadeMedida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
