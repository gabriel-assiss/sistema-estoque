package com.projeto.estoque.entity;

import com.projeto.estoque.enums.TipoMovimentacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_movimentacao")
    private TipoMovimentacao tipoDeMovimentacao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_estoque_id", nullable = false)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_funcionario_id", nullable = false)
    private Funcionario funcionario;

    public MovimentacaoEstoque() {
    }

    public MovimentacaoEstoque(Long id, LocalDate data, TipoMovimentacao tipoDeMovimentacao, Integer quantidade, Estoque estoque, Funcionario funcionario) {
        this.id = id;
        this.data = data;
        this.tipoDeMovimentacao = tipoDeMovimentacao;
        this.quantidade = quantidade;
        this.estoque = estoque;
        this.funcionario = funcionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData(LocalDate now) {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoMovimentacao getTipoDeMovimentacao() {
        return tipoDeMovimentacao;
    }

    public void setTipoDeMovimentacao(TipoMovimentacao tipoDeMovimentacao) {
        this.tipoDeMovimentacao = tipoDeMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
