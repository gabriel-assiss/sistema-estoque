package com.projeto.estoque.dto.funcionario;

import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.enums.StatusFuncionario;

public class FuncionarioResponseDTO extends FuncionarioDTO {

    public FuncionarioResponseDTO() {
        super();
    }

    public FuncionarioResponseDTO(Long id, String nome, String matricula, String cargo, String role, StatusFuncionario statusFuncionario, String email) {
        super(id, nome, matricula, cargo, role, statusFuncionario, email);
    }

    public FuncionarioResponseDTO(Funcionario funcionario) {
        super(funcionario);
    }
}
