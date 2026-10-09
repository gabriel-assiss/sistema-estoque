package com.projeto.estoque.service;

import com.projeto.estoque.entity.MovimentacaoEstoque;
import com.projeto.estoque.exception.FuncionarioNaoEncontradoException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.repository.FuncionarioRepository;
import com.projeto.estoque.repository.MovimentacaoEstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

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


    public List<MovimentacaoEstoque> movimentacaoPorFuncionario(Long funcionarioId){
        if(!funcionarioRepository.existsById(funcionarioId)){
            throw new FuncionarioNaoEncontradoException("funcionario não existe");
        }

        return movimentacaoEstoqueRepository.findAllByFuncionarioId(funcionarioId);
    }
    public List<MovimentacaoEstoque> movimentacaoPorFuncionarioEProduto(Long produtoId,
                                                                        Long funcionarioId){
        if(!funcionarioRepository.existsById(funcionarioId) && !produtoRepository.existsById(produtoId)){
            throw new FuncionarioNaoEncontradoException("funcionario e/ou produto não existe");
        }

        return movimentacaoEstoqueRepository.findAllByEstoqueProdutoIdAndFuncionarioId(produtoId,funcionarioId);
    }

    public List<MovimentacaoEstoque> movimentacaoPorProduto(Long produtoId){
        if (!produtoRepository.existsById(produtoId)){
            throw new ProdutoNaoEncontradoExeption("Produto não encontrado");
        }
        return movimentacaoEstoqueRepository.findAllByEstoqueProdutoId(produtoId);
    }
}
