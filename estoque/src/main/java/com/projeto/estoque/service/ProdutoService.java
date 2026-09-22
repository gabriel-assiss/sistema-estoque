package com.projeto.estoque.service;

import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.enums.StatusProduto;
import com.projeto.estoque.exception.ProdutoExistenteException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.exception.StatusInvalidoException;
import com.projeto.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvar(Produto produto) {
        if (produtoRepository.existsByNome(produto.getNome())) {
            throw new ProdutoExistenteException("Produto já cadastrado");
        }

        return produtoRepository.save(produto);

    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarParaInativo(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoExeption("Produto não encontrado"));

        if (produto.getStatusProduto()== StatusProduto.INATIVO){
            throw new StatusInvalidoException("Produto com status inativo");
        }

        produto.setStatusProduto(StatusProduto.INATIVO);
        return produtoRepository.save(produto);
    }
    public Produto atualizarParaAtivo(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoExeption("Produto não encontrado"));

        if (produto.getStatusProduto()== StatusProduto.ATIVO){
            throw new StatusInvalidoException("Produto com status ativo");
        }

        produto.setStatusProduto(StatusProduto.ATIVO);

        return produtoRepository.save(produto);
    }


    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }
}
