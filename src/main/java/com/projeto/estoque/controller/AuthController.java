package com.projeto.estoque.controller;

import com.projeto.estoque.dto.funcionario.FuncionarioLoginResponseDTO;
import com.projeto.estoque.dto.funcionario.LoginFuncionarioDTO;
import com.projeto.estoque.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authservice;

    public AuthController(AuthService authservice) {
        this.authservice = authservice;
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioLoginResponseDTO> login(@Valid @RequestBody LoginFuncionarioDTO login) {
        return ResponseEntity.ok(authservice.autenticar(login));
    }

}
