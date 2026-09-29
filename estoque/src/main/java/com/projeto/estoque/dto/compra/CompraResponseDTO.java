package com.projeto.estoque.dto.compra;

import com.projeto.estoque.entity.Compra;
import com.projeto.estoque.enums.StatusCompra;

import java.time.LocalDate;

public class CompraResponseDTO {
    private Long id;
    private LocalDate dataCompra;
    private StatusCompra statusCompra;
    
    public CompraResponseDTO() {}
    
    public CompraResponseDTO(Compra compra) {
        this.id = compra.getId();
        this.dataCompra = compra.getDataCompra();
        this.statusCompra = compra.getStatusCompra();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }
    public StatusCompra getStatusCompra() { return statusCompra; }
    public void setStatusCompra(StatusCompra statusCompra) { this.statusCompra = statusCompra; }
}
