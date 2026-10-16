package com.projeto.estoque.exception;

import com.projeto.estoque.entity.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNaoEncontradoException.class)
    public ResponseEntity<String> categorianaoencontrada(CategoriaNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ProdutoNaoEncontradoExeption.class)
    public ResponseEntity<String> produtonaoencontrado(ProdutoNaoEncontradoExeption ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(FornecedorNaoEncontradoException.class)
    public ResponseEntity<String> fornecedornaoencontrado(FornecedorNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    public ResponseEntity<String> funcionarionaoencontrado(FuncionarioNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<String> estoqueinsufuciente(EstoqueInsuficienteException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    @ExceptionHandler(CompraNaoEncontradaException.class)
    public ResponseEntity<String> compranaoencontrada(CompraNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    } @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<String> statusinvalido(StatusInvalidoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }@ExceptionHandler(FornecedorExistenteException.class)
    public ResponseEntity<String> fornecedorexistente(FornecedorExistenteException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }@ExceptionHandler(FuncionarioExistenteException.class)
    public ResponseEntity<String> funcionarioexistente(FuncionarioExistenteException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
