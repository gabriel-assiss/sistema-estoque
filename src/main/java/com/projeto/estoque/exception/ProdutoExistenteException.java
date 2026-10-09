package com.projeto.estoque.exception;

public class ProdutoExistenteException extends RuntimeException {
    public ProdutoExistenteException(String message) {
        super(message);
    }
}
