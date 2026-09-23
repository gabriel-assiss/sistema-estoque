package com.projeto.estoque.dto.produto;

import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.enums.StatusProduto;

public class ProdutoResponseDTO extends ProdutoDTO {

    public ProdutoResponseDTO() {
        super();
    }

    public ProdutoResponseDTO(Long id, String nome, String unidadeMedida, StatusProduto statusProduto, Long categoriaId, String categoriaNome) {
        super(id, nome, unidadeMedida, statusProduto, categoriaId, categoriaNome);
    }

    public ProdutoResponseDTO(Produto produto) {
        super(produto);
    }
}
