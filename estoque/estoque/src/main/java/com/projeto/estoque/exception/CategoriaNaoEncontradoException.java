package com.projeto.estoque.exception;

public class CategoriaNaoEncontradoException extends RuntimeException {
    public CategoriaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
