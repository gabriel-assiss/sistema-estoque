package com.projeto.estoque.dto.compra;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItemCompraRequestDTO {
    @NotNull(message = "Produto deve ser informado")
    @Positive(message = "Produto deve ser válido")
    private Long produtoId;

    @Positive(message = "Quantidade deve ser positiva")
    private int quantidade;

    @NotNull(message = "Preço unitário deve ser informado")
    @Positive(message = "Preço unitário deve ser positivo")
    private BigDecimal precoUnitario;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
