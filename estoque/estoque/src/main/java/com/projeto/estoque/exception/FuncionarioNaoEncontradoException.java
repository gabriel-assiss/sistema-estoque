package com.projeto.estoque.exception;

public class FuncionarioNaoEncontradoException extends RuntimeException {
    public FuncionarioNaoEncontradoException(String message) {
        super(message);
    }
}
