package com.projeto.estoque.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public class CategoriaSaveRequetDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    public CategoriaSaveRequetDTO() {
    }

    public CategoriaSaveRequetDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
