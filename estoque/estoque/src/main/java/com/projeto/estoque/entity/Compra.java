package com.projeto.estoque.entity;

import com.projeto.estoque.enums.StatusCompra;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_compra")
    private StatusCompra statusCompra;

    @Column(name = "data_compra")
    private LocalDate dataCompra;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_funcionario_id", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "compra",cascade = CascadeType.ALL)
    private List<ItemCompra> itensCompra = new ArrayList<>();

    public Compra() {
    }

    public Compra(Long id, StatusCompra statusCompra, Funcionario funcionario, Fornecedor fornecedor) {
        this.id = id;
        this.statusCompra = statusCompra;
        this.funcionario = funcionario;
        this.fornecedor = fornecedor;
    }
    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public void setStatusCompra(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }
}
