
package com.projeto.estoque.service;

import com.projeto.estoque.dto.estoque.EstoqueResponseDTO;
import com.projeto.estoque.entity.Estoque;
import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.exception.EstoqueInsuficienteException;
import com.projeto.estoque.repository.EstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

   @Test
    public void deveAdicionarEstoqueSeForVálido(){
    Long produtoId = 1L;
    int quantidade = 2;

    estoqueService.atualizarQuantidadeMais(produtoId,quantidade);

    verify(estoqueRepository,times(1)).adicionarEstoque(produtoId,quantidade);

   }

   @Test
    public void deveLancarExcecaoSeEstoqueForInvalido(){
       Long produtoId = 1L;
       int quantidade = 0;

       assertThrows(IllegalArgumentException.class,
               () -> estoqueService.atualizarQuantidadeMais(produtoId,quantidade));

       verify(estoqueRepository,never()).adicionarEstoque(produtoId,quantidade);
   }


    @Test
    void deveConverterEstoqueParaResponseDTO() {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(20L);

        Estoque estoque = new Estoque(1L, 15, produto);

        Page<Estoque> pagina = new PageImpl<>(
                List.of(estoque),
                pageable,
                1
        );

        when(estoqueRepository.findAll(pageable)).thenReturn(pagina);

        Page<EstoqueResponseDTO> resultado =
                estoqueService.listarTodos(pageable);

                assertNotNull(resultado);
                assertEquals(resultado.getContent().size(), pagina.getContent().size());

                EstoqueResponseDTO dto = resultado.getContent().get(0);

                assertEquals(dto.getId(),estoque.getId());
                assertEquals(dto.getQuantidadeEstoque(),estoque.getQuantidadeEstoque());
                assertEquals(estoque.getProduto().getId(),dto.getProdutoId());

        verify(estoqueRepository).findAll(pageable);




    }

    @Test
    void deveEnviarArgumentosCorretosAoRepositorio() {

        // Arrange
        Long produtoId = 1L;
        int quantidade = 5;

        // Act
        estoqueService.atualizarQuantidadeMais(produtoId,quantidade);


        ArgumentCaptor<Long> produtoIdCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Integer> quantidadeCaptor =
                ArgumentCaptor.forClass(Integer.class);


        verify(estoqueRepository).adicionarEstoque(
                produtoIdCaptor.capture(),
                quantidadeCaptor.capture()
        );


        Long id =produtoIdCaptor.getValue();
        Integer quan = quantidadeCaptor.getValue();

        assertEquals(1,id);
        assertEquals(5,quan);
    }

}
