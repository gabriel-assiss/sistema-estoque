package com.projeto.estoque.service;

import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.enums.StatusFornecedor;
import com.projeto.estoque.exception.FornecedorExistenteException;
import com.projeto.estoque.exception.FornecedorNaoEncontradoException;
import com.projeto.estoque.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public Fornecedor salvar(Fornecedor fornecedor) {
        if(fornecedorRepository.existsById(fornecedor.getId())){
            throw new FornecedorExistenteException("Fornecedor já existe");
        }else{
            return fornecedorRepository.save(fornecedor);
        }
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {

        return fornecedorRepository.findById(id);
    }

    public Fornecedor inativarFornecedor(Long idFornecedor){
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        );
        fornecedor.setStatusFornecedor(StatusFornecedor.INATIVO);

        return fornecedorRepository.save(fornecedor);

    }

    public Fornecedor ativarFornecedor(Long idFornecedor){
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        );
        fornecedor.setStatusFornecedor(StatusFornecedor.ATIVO);

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Long id, Fornecedor fornecedor) {
        fornecedor.setId(id);
        return fornecedorRepository.save(fornecedor);
    }
}
