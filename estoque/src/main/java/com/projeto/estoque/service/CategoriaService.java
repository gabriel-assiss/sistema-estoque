package com.projeto.estoque.service;

import com.projeto.estoque.dto.categoria.CategoriaDTO;
import com.projeto.estoque.dto.categoria.CategoriaResponseDTO;
import com.projeto.estoque.dto.categoria.CategoriaSaveRequetDTO;
import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.enums.StatusCategoria;
import com.projeto.estoque.exception.CategoriaNaoEncontradoException;
import com.projeto.estoque.repository.CategoriaRepository;
import org.jspecify.annotations.NonNull;
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

    public List<CategoriaResponseDTO> listarTodos() {
        return categoriaRepository.findAll().stream().map(this::transformarEmResponseDTO).toList();
    }

    public List<CategoriaResponseDTO> buscarCategoriasAtivas(Long id) {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponseDTO> ativos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            if (categoria.getStatusCategoria() == StatusCategoria.ATIVO){
                ativos.add(transformarEmResponseDTO(categoria));
            }
        }
        return ativos;
    }
    public List<CategoriaResponseDTO> buscarCategoriasInativas(Long id) {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponseDTO> inativos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            if (categoria.getStatusCategoria() == StatusCategoria.INATIVO){
                inativos.add(transformarEmResponseDTO(categoria));
            }
        }
        return inativos;
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

    public List<CategoriaDTO> buscarPorNome(String nome) {
        List<Categoria> categorias = categoriaRepository.findAllByNomeContainingIgnoreCase(nome);
        if (categorias.isEmpty()) {
            throw new CategoriaNaoEncontradoException("Nenhuma categoria encontrada com o nome: " + nome);
        }
        return categorias.stream().map(CategoriaDTO::new).toList();
    }


}
