package com.projeto.estoque.dto.funcionario;

import com.projeto.estoque.enums.RoleFuncionario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FuncionarioSaveRequetDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotNull(message = "Perfil deve ser informado")
    private RoleFuncionario role;

    @NotBlank(message = "E-mail não pode ser vazio")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

    @NotBlank(message = "Cargo não pode ser vazio")
    private String cargo;

    @NotBlank(message = "Matrícula não pode ser vazia")
    private String matricula;


    public FuncionarioSaveRequetDTO() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
