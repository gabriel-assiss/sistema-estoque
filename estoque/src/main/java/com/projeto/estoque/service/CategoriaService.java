package com.projeto.estoque.service;

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

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> buscarCategoriasAtivas(Long id) {
     List<Categoria> categorias = categoriaRepository.findAll();
     List<Categoria> ativos = new ArrayList<>();
    for (Categoria categoria : categorias) {
        if (categoria.getStatusCategoria() == StatusCategoria.ATIVO){
            ativos.add(categoria);
        }
    }
    return ativos;
    }
    public List<Categoria> buscarCategoriasInativas(Long id) {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<Categoria> inativos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            if (categoria.getStatusCategoria() == StatusCategoria.INATIVO){
                inativos.add(categoria);
            }
        }
        return inativos;
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }


    public Categoria atualizar(Long id, @NonNull Categoria categoria) {
       Categoria categoriaEncontrada = categoriaRepository.findById(id).orElseThrow(
               ()-> new CategoriaNaoEncontradoException("Impossivel atualizar o categoria, categoriia não encontrada")
       );
       return categoriaRepository.save(categoriaEncontrada);
    }

    public void deletarPorId(Long id) {
        Categoria categoriaEncontrada = categoriaRepository.findById(id).orElseThrow(()->
            new CategoriaNaoEncontradoException("Categoria não encontrada"));

        categoriaRepository.delete(categoriaEncontrada);

    }
}
