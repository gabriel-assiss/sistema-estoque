package com.projeto.estoque.security;

import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    FuncionarioRepository funcionarioRepository;

    CustomUserDetailsService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository=funcionarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(username);
        return funcionario;
    }
}

