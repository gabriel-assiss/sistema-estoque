package com.projeto.estoque.service;

import com.projeto.estoque.entity.*;
import com.projeto.estoque.enums.StatusCompra;
import com.projeto.estoque.exception.CompraNaoEncontradaException;
import com.projeto.estoque.exception.EstoqueInsuficienteException;
import com.projeto.estoque.exception.FuncionarioNaoEncontradoException;
import com.projeto.estoque.exception.StatusInvalidoException;
import com.projeto.estoque.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @Mock
    private ItemCompraRepository itemCompraRepository;

    @InjectMocks
    private CompraService compraService;

    @Test
    void LancarExcecaoSeCompraNaoExiste(){
        Long idCompra = 1L;
        Long idFuncionario = 2L;

        when(compraRepository.findById(idCompra)).thenReturn(Optional.empty());

        assertThrows(CompraNaoEncontradaException.class,
                ()->compraService.cancelar(idCompra,idFuncionario));

        verify(compraRepository).findById(idCompra);

        verifyNoInteractions(
                produtoRepository,
                fornecedorRepository,
                funcionarioRepository,
                movimentacaoEstoqueRepository,
                itemCompraRepository
        );

    }

    @Test
    void LancarExcecaoSeFuncionarioNaoExiste(){
        Long idCompra = 1L;
        Long idFuncionario = 2L;
    Compra compra = new Compra();
        when(funcionarioRepository.findById(idFuncionario)).thenReturn(Optional.empty());
        when(compraRepository.findById(idCompra)).thenReturn(Optional.of(compra));

        assertThrows(FuncionarioNaoEncontradoException.class,
                ()->compraService.cancelar(idCompra,idFuncionario));

        verify(funcionarioRepository).findById(idFuncionario);
        verify(compraRepository).findById(idCompra);

        verifyNoInteractions(
                produtoRepository,
                fornecedorRepository,
                movimentacaoEstoqueRepository,
                itemCompraRepository
        );

    }
    @Test
    void deveLancarExcecaoQuandoCompraJaEstiverCancelada(){
        Long idCompra = 1L;
        Long idFuncionario = 2L;
        StatusCompra statusCompra = StatusCompra.CANCELADA;
        Compra compra = new Compra();
        compra.setStatusCompra(statusCompra);
        Funcionario funcionario = new Funcionario();
        when(funcionarioRepository.findById(idFuncionario)).thenReturn(Optional.of(funcionario));
        when(compraRepository.findById(idCompra)).thenReturn(Optional.of(compra));
        assertThrows(StatusInvalidoException.class,
                ()->compraService.cancelar(idCompra,idFuncionario));

        verify(funcionarioRepository).findById(idFuncionario);
        verify(compraRepository).findById(idCompra);
        verify(compraRepository, never()).save(any(Compra.class));

        verifyNoInteractions(
                produtoRepository,
                fornecedorRepository,
                movimentacaoEstoqueRepository,
                itemCompraRepository
        );

    }
    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente(){
        Long idCompra = 1L;
        Long idFuncionario = 2L;
        Produto produto1 = new Produto();
        Estoque estoque1 = new Estoque();
        estoque1.setQuantidadeEstoque(1);
        produto1.setEstoque(estoque1);

        StatusCompra statusCompra = StatusCompra.CONCLUIDA;
        Compra compra = new Compra();
        compra.setStatusCompra(statusCompra);

        ItemCompra itemCompra1 = new ItemCompra();
        itemCompra1.setProduto(produto1);
        itemCompra1.setQuantidade(23);

        List<ItemCompra> itens = Arrays.asList(itemCompra1);



        Funcionario funcionario = new Funcionario();
        when(funcionarioRepository.findById(idFuncionario)).thenReturn(Optional.of(funcionario));
        when(compraRepository.findById(idCompra)).thenReturn(Optional.of(compra));
        when(itemCompraRepository.findByCompra(compra)).thenReturn(itens);


        assertThrows(EstoqueInsuficienteException.class,
                ()->compraService.cancelar(idCompra,idFuncionario));


        assertEquals(1, estoque1.getQuantidadeEstoque());
        verify(itemCompraRepository).findByCompra(compra);
        verify(funcionarioRepository).findById(idFuncionario);
        verify(compraRepository).findById(idCompra);
        verify(compraRepository, never()).save(any(Compra.class));

        verifyNoInteractions(
                fornecedorRepository,
                movimentacaoEstoqueRepository,
                produtoRepository
        );

    }

    void deveCancelarCompraQuandoEstoqueForSuficiente(){
        Long idCompra = 1L;
        Long idFuncionario = 2L;
        Compra compra = new Compra();
        compra.setId(idCompra);
        StatusCompra statusCompra = StatusCompra.CONCLUIDA;
        compra.setStatusCompra(statusCompra);
        ItemCompra itemCompra1 = new ItemCompra();
        Produto produto1 = new Produto();
        itemCompra1.setProduto(produto1);
        Funcionario funcionario = new Funcionario();

        Estoque estoque = new Estoque();
        estoque.setQuantidadeEstoque(30);
        estoque.setProduto(produto1);
        produto1.setEstoque(estoque);

        itemCompra1.setQuantidade(5);
        List<ItemCompra> itens = Arrays.asList(itemCompra1);

    }
}