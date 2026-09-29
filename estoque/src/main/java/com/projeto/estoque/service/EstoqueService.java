package com.projeto.estoque.service;

import com.projeto.estoque.dto.estoque.EstoqueResponseDTO;
import com.projeto.estoque.entity.Estoque;
import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.exception.EstoqueInsuficienteException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.repository.EstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

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

    public List<EstoqueResponseDTO> listarTodos() {
        return estoqueRepository.findAll().stream().map(this::transformarEmResponseDTO).toList();
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        return transformarEmResponseDTO(estoqueRepository.findById(id).orElseThrow(() -> new RuntimeException("Estoque não encontrado")));
    }


    public void atualizarQuantidadeMais(Long idProduto, int quantidade) {
        Produto produto = produtoRepository.findById(idProduto).orElseThrow(
                ()-> new ProdutoNaoEncontradoExeption("produto não encontrado")
        );
        Estoque estoque = produto.getEstoque();
        estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque()+quantidade);
    }

    public void atualizarQuantidadeMenos(Long idProduto, int quantidade) {
        Produto produto = produtoRepository.findById(idProduto).orElseThrow(
                ()-> new ProdutoNaoEncontradoExeption("produto não encontrado")
        );
        Estoque estoque = produto.getEstoque();
        if(estoque.getQuantidadeEstoque()>quantidade){
            estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque()-quantidade);
        }else{
            throw new EstoqueInsuficienteException("Impossivel realizar subtração, estoque ficará negativo");
        }

    }

}
