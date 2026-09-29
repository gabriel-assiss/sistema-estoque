package com.projeto.estoque.dto.estoque;

import com.projeto.estoque.entity.Estoque;

public class EstoqueResponseDTO {
    private Long id;
    private int quantidadeEstoque;
    private Long produtoId;
    
    public EstoqueResponseDTO() {}
    
    public EstoqueResponseDTO(Estoque estoque) {
        this.id = estoque.getId();
        this.quantidadeEstoque = estoque.getQuantidadeEstoque();
        if (estoque.getProduto() != null) {
            this.produtoId = estoque.getProduto().getId();
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
}
