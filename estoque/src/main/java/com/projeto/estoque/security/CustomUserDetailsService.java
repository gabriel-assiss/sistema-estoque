package com.projeto.estoque.security;

import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    public CustomUserDetailsService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(username);
        if (funcionario == null) {
            throw new UsernameNotFoundException("Funcionário não encontrado com o e-mail: " + username);
        }
        return funcionario;
    }
}
