package com.projeto.estoque.service;

import com.projeto.estoque.dto.funcionario.FuncionarioLoginResponseDTO;
import com.projeto.estoque.dto.funcionario.LoginFuncionarioDTO;
import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager , JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public FuncionarioLoginResponseDTO autenticar(LoginFuncionarioDTO loginFuncionarioDTO) {
        Authentication autenticacao =
                new UsernamePasswordAuthenticationToken(
                        loginFuncionarioDTO.getEmail(),
                        loginFuncionarioDTO.getSenha()
                );
        Authentication autenticado = authenticationManager.authenticate(
                autenticacao
        );
        Funcionario funcionario = (Funcionario) autenticado.getPrincipal();
        String token = jwtService.gerarToken(funcionario);

        FuncionarioLoginResponseDTO funcionarioLoginResponseDTO = new FuncionarioLoginResponseDTO();
        funcionarioLoginResponseDTO.setToken(token);
        funcionarioLoginResponseDTO.setEmail(funcionario.getEmail());
        funcionarioLoginResponseDTO.setNome(funcionario.getNome());
        funcionarioLoginResponseDTO.setRole(funcionario.getRoleFuncionario().name());
        funcionarioLoginResponseDTO.setId(funcionario.getId());


        return funcionarioLoginResponseDTO;

    }
}