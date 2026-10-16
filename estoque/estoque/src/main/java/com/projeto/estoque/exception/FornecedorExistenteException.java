package com.projeto.estoque.exception;

public class FornecedorExistenteException extends RuntimeException {
    public FornecedorExistenteException(String message) {
        super(message);
    }
}
