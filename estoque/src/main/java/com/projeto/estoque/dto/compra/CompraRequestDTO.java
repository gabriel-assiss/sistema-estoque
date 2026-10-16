package com.projeto.estoque.dto.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class CompraRequestDTO {

    @NotNull(message = "Funcionário deve ser informado")
    @Positive(message = "Funcionário deve ser válido")
    private Long funcionarioId;

    @NotNull(message = "Fornecedor deve ser informado")
    @Positive(message = "Fornecedor deve ser válido")
    private Long fornecedorId;

    @NotEmpty(message = "A compra deve conter ao menos um item")
    @Valid
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
