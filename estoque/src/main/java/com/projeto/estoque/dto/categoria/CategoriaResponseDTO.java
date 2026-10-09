package com.projeto.estoque.dto.categoria;

import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.enums.StatusCategoria;

public class CategoriaResponseDTO extends CategoriaDTO {

    public CategoriaResponseDTO() {
        super();
    }

    public CategoriaResponseDTO(Long id, String nome, StatusCategoria statusCategoria) {
        super(id, nome, statusCategoria);
    }

    public CategoriaResponseDTO(Categoria categoria) {
        super(categoria);
    }
}
