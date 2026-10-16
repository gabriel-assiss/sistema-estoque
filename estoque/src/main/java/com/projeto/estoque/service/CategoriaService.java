package com.projeto.estoque.service;

import com.projeto.estoque.dto.categoria.CategoriaDTO;
import com.projeto.estoque.dto.categoria.CategoriaResponseDTO;
import com.projeto.estoque.dto.categoria.CategoriaSaveRequetDTO;
import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.enums.StatusCategoria;
import com.projeto.estoque.exception.CategoriaNaoEncontradoException;
import com.projeto.estoque.repository.CategoriaRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria transformarDTO(CategoriaSaveRequetDTO categoriaDto){
        Categoria categoria = new Categoria();
        categoria.setStatusCategoria(StatusCategoria.ATIVO);
        categoria.setNome(categoriaDto.getNome());
        return categoria;
    }

    public CategoriaResponseDTO transformarEmResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria);
    }

    public CategoriaResponseDTO salvar(CategoriaSaveRequetDTO categoriaDTO) {
        Categoria categoria = transformarDTO(categoriaDTO);
        if (categoriaRepository.existsByNome(categoria.getNome())){
            throw new CategoriaNaoEncontradoException("Categoria existente");
        }else{
            Categoria salvo = categoriaRepository.save(categoria);
            return transformarEmResponseDTO(salvo);
        }
    }

    public Page<CategoriaResponseDTO> listarTodos(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(this::transformarEmResponseDTO);
    }

    public Page<CategoriaResponseDTO> buscarCategoriasAtivas(Pageable pageable) {
        return categoriaRepository.findAllByStatusCategoria(pageable, StatusCategoria.ATIVO).map(this::transformarEmResponseDTO);
    }

    public Page<CategoriaResponseDTO> buscarCategoriasInativas(Pageable pageable) {
        return categoriaRepository.findAllByStatusCategoria(pageable, StatusCategoria.INATIVO).map(this::transformarEmResponseDTO);
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                ()-> new CategoriaNaoEncontradoException("Categoria não encontrada")
        );
        return transformarEmResponseDTO(categoria);
    }


    public CategoriaResponseDTO atualizar(Long id, @NonNull Categoria categoria) {
       Categoria categoriaEncontrada = categoriaRepository.findById(id).orElseThrow(
               ()-> new CategoriaNaoEncontradoException("Impossivel atualizar o categoria, categoriia não encontrada")
       );
       categoriaEncontrada.setNome(categoria.getNome());
       categoriaEncontrada.setStatusCategoria(categoria.getStatusCategoria());
       Categoria salvo = categoriaRepository.save(categoriaEncontrada);
       return transformarEmResponseDTO(salvo);
    }

    public void deletarPorId(Long id) {
        Categoria categoriaEncontrada = categoriaRepository.findById(id).orElseThrow(()->
            new CategoriaNaoEncontradoException("Categoria não encontrada"));

        categoriaRepository.delete(categoriaEncontrada);

    }

    public Page<CategoriaDTO> buscarPorNome(Pageable pageable, String nome) {
        Page<Categoria> categorias = categoriaRepository.findAllByNomeContainingIgnoreCase(pageable, nome);
        if (categorias.isEmpty()) {
            throw new CategoriaNaoEncontradoException("Nenhuma categoria encontrada com o nome: " + nome);
        }
        return categorias.map(CategoriaDTO::new);
    }


}
