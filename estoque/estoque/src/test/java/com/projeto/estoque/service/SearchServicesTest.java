package com.projeto.estoque.service;

import com.projeto.estoque.dto.categoria.CategoriaDTO;
import com.projeto.estoque.dto.fornecedor.FornecedorDTO;
import com.projeto.estoque.dto.funcionario.FuncionarioDTO;
import com.projeto.estoque.dto.produto.ProdutoDTO;
import com.projeto.estoque.entity.Categoria;
import com.projeto.estoque.entity.Fornecedor;
import com.projeto.estoque.entity.Funcionario;
import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.enums.StatusCategoria;
import com.projeto.estoque.enums.StatusFornecedor;
import com.projeto.estoque.enums.StatusFuncionario;
import com.projeto.estoque.enums.StatusProduto;
import com.projeto.estoque.exception.CategoriaNaoEncontradoException;
import com.projeto.estoque.exception.FornecedorNaoEncontradoException;
import com.projeto.estoque.exception.FuncionarioNaoEncontradoException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.repository.CategoriaRepository;
import com.projeto.estoque.repository.FornecedorRepository;
import com.projeto.estoque.repository.FuncionarioRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServicesTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    void produtoBuscarPorNome_quandoEncontrado_deveRetornarListaDTOComId() {
        Produto produto = new Produto(1L, "Teclado Mecanico", "UN", StatusProduto.ATIVO, null);
        when(produtoRepository.findAllByNomeContainingIgnoreCase("teclado"))
                .thenReturn(List.of(produto));

        List<ProdutoDTO> resultado = produtoService.buscarPorNome("teclado");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.getFirst().getId());
        assertEquals("Teclado Mecanico", resultado.getFirst().getNome());
    }

    @Test
    void produtoBuscarPorNome_quandoNaoEncontrado_deveLancarExcecao() {
        when(produtoRepository.findAllByNomeContainingIgnoreCase("inexistente"))
                .thenReturn(Collections.emptyList());

        assertThrows(ProdutoNaoEncontradoExeption.class, () ->
                produtoService.buscarPorNome("inexistente")
        );
    }

    @Test
    void funcionarioBuscarPorNome_quandoEncontrado_deveOcultarSenhaERetornarId() {
        Funcionario funcionario = new Funcionario(10L, "Gabriel Silva", "MAT123", "Gerente", "ADMIN", "gabriel@email.com", "senhaSuperSecreta123");
        funcionario.setStatusFuncionario(StatusFuncionario.ATIVO);
        when(funcionarioRepository.findAllByNomeContainingIgnoreCase("gabriel"))
                .thenReturn(List.of(funcionario));

        List<FuncionarioDTO> resultado = funcionarioService.buscarPorNome("gabriel");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        FuncionarioDTO dto = resultado.getFirst();
        assertEquals(10L, dto.getId());
        assertEquals("Gabriel Silva", dto.getNome());
        assertEquals("gabriel@email.com", dto.getEmail());
        // Garante que o DTO não possui campo nem método de senha
        assertFalse(dto.getClass().getDeclaredFields().length == 0);
        for (var field : dto.getClass().getDeclaredFields()) {
            assertNotEquals("senha", field.getName().toLowerCase());
        }
    }

    @Test
    void funcionarioBuscarPorNome_quandoNaoEncontrado_deveLancarExcecao() {
        when(funcionarioRepository.findAllByNomeContainingIgnoreCase("inexistente"))
                .thenReturn(Collections.emptyList());

        assertThrows(FuncionarioNaoEncontradoException.class, () ->
                funcionarioService.buscarPorNome("inexistente")
        );
    }

    @Test
    void categoriaBuscarPorNome_quandoEncontrado_deveRetornarListaDTOComId() {
        Categoria categoria = new Categoria(5L, "Eletronicos", StatusCategoria.ATIVO);
        when(categoriaRepository.findAllByNomeContainingIgnoreCase("eletr"))
                .thenReturn(List.of(categoria));

        List<CategoriaDTO> resultado = categoriaService.buscarPorNome("eletr");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.getFirst().getId());
        assertEquals("Eletronicos", resultado.getFirst().getNome());
    }

    @Test
    void categoriaBuscarPorNome_quandoNaoEncontrado_deveLancarExcecao() {
        when(categoriaRepository.findAllByNomeContainingIgnoreCase("inexistente"))
                .thenReturn(Collections.emptyList());

        assertThrows(CategoriaNaoEncontradoException.class, () ->
                categoriaService.buscarPorNome("inexistente")
        );
    }

    @Test
    void fornecedorBuscarPorNome_quandoEncontrado_deveRetornarListaDTOComId() {
        Fornecedor fornecedor = new Fornecedor(20L, "Distribuidora Tech", StatusFornecedor.ATIVO);
        fornecedor.setCnpj("12.345.678/0001-99");
        when(fornecedorRepository.findAllByNomeContainingIgnoreCase("tech"))
                .thenReturn(List.of(fornecedor));

        List<FornecedorDTO> resultado = fornecedorService.buscarPorNome("tech");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(20L, resultado.getFirst().getId());
        assertEquals("Distribuidora Tech", resultado.getFirst().getNome());
        assertEquals("12.345.678/0001-99", resultado.getFirst().getCnpj());
    }

    @Test
    void fornecedorBuscarPorNome_quandoNaoEncontrado_deveLancarExcecao() {
        when(fornecedorRepository.findAllByNomeContainingIgnoreCase("inexistente"))
                .thenReturn(Collections.emptyList());

        assertThrows(FornecedorNaoEncontradoException.class, () ->
                fornecedorService.buscarPorNome("inexistente")
        );
    }
}
