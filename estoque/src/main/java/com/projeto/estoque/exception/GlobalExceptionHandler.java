package com.projeto.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro ->
                erros.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Requisição inválida."));
    }

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


    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, String>> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex) {

        Map<String, String> erro = new LinkedHashMap<>();

        erro.put("erro", "Conflito de atualização.");
        erro.put("mensagem", "Este registro foi modificado por outro usuário. Atualize os dados e tente novamente.");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
}
