package com.projeto.estoque.exception;

public class FuncionarioExistenteException extends RuntimeException {
    public FuncionarioExistenteException(String message) {
        super(message);
    }
}
