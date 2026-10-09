package com.projeto.estoque.dto.funcionario;

import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.enums.StatusFuncionario;

public class FuncionarioDTO {

    private Long id;
    private String nome;
    private String matricula;
    private String cargo;
    private String role;
    private StatusFuncionario statusFuncionario;
    private String email;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(Long id, String nome, String matricula, String cargo, String role, StatusFuncionario statusFuncionario, String email) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.cargo = cargo;
        this.role = role;
        this.statusFuncionario = statusFuncionario;
        this.email = email;
    }

    public FuncionarioDTO(Funcionario funcionario) {
        if (funcionario != null) {
            this.id = funcionario.getId();
            this.nome = funcionario.getNome();
            this.matricula = funcionario.getMatricula();
            this.cargo = funcionario.getCargo();
            this.role = funcionario.getRole();
            this.statusFuncionario = funcionario.getStatusFuncionario();
            this.email = funcionario.getEmail();
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public StatusFuncionario getStatusFuncionario() {
        return statusFuncionario;
    }

    public void setStatusFuncionario(StatusFuncionario statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
