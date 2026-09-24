package com.projeto.estoque.dto.funcionario;

import com.projeto.estoque.enums.RoleFuncionario;

public class FuncionarioSaveRequetDTO {
    private String nome;
    private RoleFuncionario role;
    private String email;
    private String senha;

    public FuncionarioSaveRequetDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RoleFuncionario getRole() {
        return role;
    }

    public void setRole(RoleFuncionario role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
