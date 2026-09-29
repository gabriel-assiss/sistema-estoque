package com.projeto.estoque.service;

import com.projeto.estoque.dto.fornecedor.FornecedorDTO;
import com.projeto.estoque.dto.fornecedor.FornecedorResponseDTO;
import com.projeto.estoque.dto.fornecedor.FornecedorSaveRequetDTO;
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

    public Fornecedor transformarDto(FornecedorSaveRequetDTO fornecedordto){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setStatusFornecedor(StatusFornecedor.ATIVO);
        fornecedor.setCnpj(fornecedordto.getCnpj());
        fornecedor.setNome(fornecedordto.getNome());

        return fornecedor;
    }

    public FornecedorResponseDTO transformarEmResponseDTO(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(fornecedor);
    }

    public FornecedorResponseDTO salvar(FornecedorSaveRequetDTO fornecedorDTO) {

        Fornecedor fornecedor = transformarDto(fornecedorDTO);
        if(fornecedorRepository.existsByCnpj(fornecedor.getCnpj())){
            throw new FornecedorExistenteException("Fornecedor já existe");
        }else{
            return transformarEmResponseDTO(fornecedorRepository.save(fornecedor));
        }
    }

    public List<FornecedorResponseDTO> listarTodos() {
        return fornecedorRepository.findAll().stream().map(this::transformarEmResponseDTO).toList();
    }

    public FornecedorResponseDTO buscarPorId(Long id) {

        return transformarEmResponseDTO(fornecedorRepository.findById(id).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        ));
    }

    public FornecedorResponseDTO inativarFornecedor(Long idFornecedor){
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        );
        fornecedor.setStatusFornecedor(StatusFornecedor.INATIVO);

        return transformarEmResponseDTO(fornecedorRepository.save(fornecedor));

    }

    public FornecedorResponseDTO ativarFornecedor(Long idFornecedor){
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
        );
        fornecedor.setStatusFornecedor(StatusFornecedor.ATIVO);

        return transformarEmResponseDTO(fornecedorRepository.save(fornecedor));
    }

    public FornecedorResponseDTO atualizaNome(Long id,String nome) {
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor não encontrado")
                );
        fornecedor.setNome(nome);
        return transformarEmResponseDTO(fornecedorRepository.save(fornecedor));
    }

    public List<FornecedorDTO> buscarPorNome(String nome) {
        List<Fornecedor> fornecedores = fornecedorRepository.findAllByNomeContainingIgnoreCase(nome);
        if (fornecedores.isEmpty()) {
            throw new FornecedorNaoEncontradoException("Nenhum fornecedor encontrado com o nome: " + nome);
        }
        return fornecedores.stream().map(FornecedorDTO::new).toList();
    }


}
