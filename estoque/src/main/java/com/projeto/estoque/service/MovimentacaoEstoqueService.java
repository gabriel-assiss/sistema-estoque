package com.projeto.estoque.service;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import com.projeto.estoque.exception.FuncionarioNaoEncontradoException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.repository.FuncionarioRepository;
import com.projeto.estoque.repository.MovimentacaoEstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class MovimentacaoEstoqueService {
    MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    FuncionarioRepository funcionarioRepository;
    ProdutoRepository produtoRepository;
    MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
                               FuncionarioRepository funcionarioRepository,
                               ProdutoRepository produtoRepository){
        this.movimentacaoEstoqueRepository=movimentacaoEstoqueRepository;
        this.funcionarioRepository=funcionarioRepository;
        this.produtoRepository=produtoRepository;
    }


    public Page<MovimentacaoEstoque> movimentacaoPorFuncionario(Long funcionarioId, Pageable pageable){
        if(!funcionarioRepository.existsById(funcionarioId)){
            throw new FuncionarioNaoEncontradoException("funcionario não existe");
        }

        return movimentacaoEstoqueRepository.findAllByFuncionarioId(funcionarioId, pageable);
    }
    public Page<MovimentacaoEstoque> movimentacaoPorFuncionarioEProduto(Long produtoId,
                                                                         Long funcionarioId,
                                                                         Pageable pageable){
        if(!funcionarioRepository.existsById(funcionarioId) && !produtoRepository.existsById(produtoId)){
            throw new FuncionarioNaoEncontradoException("funcionario e/ou produto não existe");
        }

        return movimentacaoEstoqueRepository.findAllByEstoqueProdutoIdAndFuncionarioId(produtoId, funcionarioId, pageable);
    }

    public Page<MovimentacaoEstoque> movimentacaoPorProduto(Long produtoId, Pageable pageable){
        if (!produtoRepository.existsById(produtoId)){
            throw new ProdutoNaoEncontradoExeption("Produto não encontrado");
        }
        return movimentacaoEstoqueRepository.findAllByEstoqueProdutoId(produtoId, pageable);
    }
}
