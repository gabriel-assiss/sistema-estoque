package com.projeto.estoque.dto.compra;

import com.projeto.estoque.enums.StatusCompra;

public class CompraUpdateDTO {
    StatusCompra statusCompra;

    public CompraUpdateDTO(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public void setStatusCompra(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }
}
