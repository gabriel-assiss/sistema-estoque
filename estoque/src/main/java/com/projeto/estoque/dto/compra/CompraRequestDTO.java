package com.projeto.estoque.dto.compra;

import java.util.List;

public class CompraRequestDTO {

    private Long funcionarioId;
    private Long fornecedorId;
    List<ItemCompraRequestDTO> itens;

    public Long getFuncionario() {
        return funcionarioId;
    }

    public void setFuncionario(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Long getFornecedor() {
        return fornecedorId;
    }

    public void setFornecedor(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public List<ItemCompraRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompraRequestDTO> itens) {
        this.itens = itens;
    }
}
