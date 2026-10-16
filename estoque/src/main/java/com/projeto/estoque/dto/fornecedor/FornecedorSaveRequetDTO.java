package com.projeto.estoque.dto.fornecedor;

import jakarta.validation.constraints.NotBlank;

public class FornecedorSaveRequetDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "CNPJ não pode ser vazio")
    private  String cnpj;

    public FornecedorSaveRequetDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
