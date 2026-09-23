package com.projeto.estoque.service;

import com.projeto.estoque.dto.fornecedor.FornecedorDTO;
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
        if(fornecedorRepository.existsByCnpj(fornecedor.getCnpj())){
            throw new FornecedorExistenteException("Fornecedor já existe");
        }else{
            return fornecedorRepository.save(fornecedor);
        }
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor buscarPorId(Long id) {

        return fornecedorRepository.findById(id).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        );
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

    public Fornecedor atualizaNome(Long id,String nome) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
                );
        fornecedor.setNome(nome);
        return fornecedorRepository.save(fornecedor);
    }

    public List<FornecedorDTO> buscarPorNome(String nome) {
        List<Fornecedor> fornecedores = fornecedorRepository.findAllByNomeContainingIgnoreCase(nome);
        if (fornecedores.isEmpty()) {
            throw new FornecedorNaoEncontradoException("Nenhum fornecedor encontrado com o nome: " + nome);
        }
        return fornecedores.stream().map(FornecedorDTO::new).toList();
    }

    public List<FornecedorDTO> pesquisarPorNome(String nome) {
        return buscarPorNome(nome);
    }
}
