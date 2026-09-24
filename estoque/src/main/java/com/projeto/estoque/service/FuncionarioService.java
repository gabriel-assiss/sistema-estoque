package com.projeto.estoque.service;

import com.projeto.estoque.dto.funcionario.FuncionarioDTO;
import com.projeto.estoque.dto.funcionario.FuncionarioSaveRequetDTO;
import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.enums.StatusFuncionario;
import com.projeto.estoque.exception.FuncionarioExistenteException;
import com.projeto.estoque.exception.FuncionarioNaoEncontradoException;
import com.projeto.estoque.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario transformarEmDTO(FuncionarioSaveRequetDTO funcionarioDTO){
        Funcionario funcionario = new Funcionario();

        funcionario.setStatusFuncionario(StatusFuncionario.ATIVO);
        funcionario.setCargo(funcionarioDTO.getCargo());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setMatricula(funcionarioDTO.getMatricula());
        funcionario.setSenha(funcionarioDTO.getSenha());

        return funcionario;
    }

    public Funcionario salvar(FuncionarioSaveRequetDTO funcionarioDTO) {

        Funcionario funcionario = transformarEmDTO(funcionarioDTO);
        if(funcionarioRepository.existsByMatricula(funcionario.getMatricula())){
            throw new FuncionarioExistenteException("Funcionario já existe");
        }else{
            return funcionarioRepository.save(funcionario);
        }
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {

        return funcionarioRepository.findById(id).orElseThrow(
                ()-> new FuncionarioNaoEncontradoException("Funcionario não encontrado")
        );
    }

    public Funcionario inativarFuncionario(Long idFuncionario){
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow(
                ()-> new FuncionarioNaoEncontradoException("Funcionario não encontrado")
        );
        funcionario.setStatusFuncionario(StatusFuncionario.INATIVO);

        return funcionarioRepository.save(funcionario);

    }

    public Funcionario ativarFuncionario(Long idFuncionario){
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow(
                ()-> new FuncionarioNaoEncontradoException("Funcionario não encontrado")
        );
        funcionario.setStatusFuncionario(StatusFuncionario.ATIVO);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizaNome(Long id,String nome) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(
                ()-> new FuncionarioNaoEncontradoException("Funcionario não encontrado")
        );
        funcionario.setNome(nome);
        return funcionarioRepository.save(funcionario);
    }

    public List<FuncionarioDTO> buscarPorNome(String nome) {
        List<Funcionario> funcionarios = funcionarioRepository.findAllByNomeContainingIgnoreCase(nome);
        if (funcionarios.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Nenhum funcionário encontrado com o nome: " + nome);
        }
        return funcionarios.stream().map(FuncionarioDTO::new).toList();
    }


}
