package com.projeto.estoque.dto.categoria;

public class CategoriaSaveRequetDTO {
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
