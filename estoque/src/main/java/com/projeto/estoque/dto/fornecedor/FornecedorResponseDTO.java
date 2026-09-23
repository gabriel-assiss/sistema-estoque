package com.projeto.estoque.dto.fornecedor;

import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.enums.StatusFornecedor;

public class FornecedorResponseDTO extends FornecedorDTO {

    public FornecedorResponseDTO() {
        super();
    }

    public FornecedorResponseDTO(Long id, String nome, String cnpj, StatusFornecedor statusFornecedor) {
        super(id, nome, cnpj, statusFornecedor);
    }

    public FornecedorResponseDTO(Fornecedor fornecedor) {
        super(fornecedor);
    }
}
