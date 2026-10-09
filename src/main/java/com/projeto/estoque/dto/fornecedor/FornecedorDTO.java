package com.projeto.estoque.dto.fornecedor;

import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.enums.StatusFornecedor;

public class FornecedorDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private StatusFornecedor statusFornecedor;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String nome, String cnpj, StatusFornecedor statusFornecedor) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.statusFornecedor = statusFornecedor;
    }

    public FornecedorDTO(Fornecedor fornecedor) {
        if (fornecedor != null) {
            this.id = fornecedor.getId();
            this.nome = fornecedor.getNome();
            this.cnpj = fornecedor.getCnpj();
            this.statusFornecedor = fornecedor.getStatusFornecedor();
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public StatusFornecedor getStatusFornecedor() {
        return statusFornecedor;
    }

    public void setStatusFornecedor(StatusFornecedor statusFornecedor) {
        this.statusFornecedor = statusFornecedor;
    }
}
