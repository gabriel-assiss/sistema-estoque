package com.projeto.estoque.service;

import com.projeto.estoque.dto.estoque.EstoqueResponseDTO;
import com.projeto.estoque.entity.Estoque;
import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.exception.EstoqueInsuficienteException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.repository.EstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;


    public EstoqueService(EstoqueRepository estoqueRepository,ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    public EstoqueResponseDTO transformarEmResponseDTO(Estoque estoque) {
        return new EstoqueResponseDTO(estoque);
    }

    public Page<EstoqueResponseDTO> listarTodos(Pageable pageable) {
        return estoqueRepository.findAll(pageable).map(this::transformarEmResponseDTO);
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        return transformarEmResponseDTO(estoqueRepository.findById(id).orElseThrow(() -> new RuntimeException("Estoque não encontrado")));
    }


    public void atualizarQuantidadeMais(Long idProduto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positivo");
        }

        estoqueRepository.adicionarEstoque(idProduto, quantidade);

    }

    @Transactional
    public void atualizarQuantidadeMenos(Long idProduto, int quantidade) {

        if (quantidade<=0){
            throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        }
        int resposta = estoqueRepository.debitarEstoque(idProduto, quantidade);

        if (resposta==0){
            throw  new EstoqueInsuficienteException("Estoque insuficiente e/ou produto inexistente");
        }

    }

}
