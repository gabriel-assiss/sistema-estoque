package com.projeto.estoque.config;

import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.enums.RoleFuncionario;
import com.projeto.estoque.enums.StatusFuncionario;
import com.projeto.estoque.repository.FuncionarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        boolean existeAdmin = funcionarioRepository
                .existsByRole(RoleFuncionario.ADMIN);

        if (existeAdmin) {
            System.out.println("Administrador já cadastrado.");
            return;
        }

        Funcionario adm = new Funcionario();

        adm.setSenha(passwordEncoder.encode(adminPassword));
        adm.setMatricula("111111");
        adm.setEmail("adminit@gmail.com");
        adm.setStatusFuncionario(StatusFuncionario.ATIVO);
        adm.setCargo("admInicializador");
        adm.setNome("adm");
        adm.setRoleFuncionario(RoleFuncionario.ADMIN);

        funcionarioRepository.save(adm);

        System.out.println("Administrador inicial criado.");
    }
}