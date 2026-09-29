package com.projeto.estoque.service;

import com.projeto.estoque.dto.produto.ProdutoDTO;
import com.projeto.estoque.dto.produto.ProdutoResponseDTO;
import com.projeto.estoque.dto.produto.ProdutoSaveRequetDTO;
import com.projeto.estoque.entity.Estoque;
import com.projeto.estoque.entity.Produto;
import com.projeto.estoque.enums.StatusProduto;
import com.projeto.estoque.exception.CategoriaNaoEncontradoException;
import com.projeto.estoque.exception.ProdutoExistenteException;
import com.projeto.estoque.exception.ProdutoNaoEncontradoExeption;
import com.projeto.estoque.exception.StatusInvalidoException;
import com.projeto.estoque.repository.CategoriaRepository;
import com.projeto.estoque.repository.EstoqueRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto transformarDTOemProduto(ProdutoSaveRequetDTO dto){
        Produto produto = new Produto();
        produto.setStatusProduto(StatusProduto.ATIVO);
        produto.setUnidadeMedida(dto.getUnidadeMedida());
        produto.setNome(dto.getNome());
        produto.setCategoria(categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(
                ()-> new CategoriaNaoEncontradoException("Categoria não encontrada")
        ));

        return produto;
    }

    public ProdutoResponseDTO transformarEmResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoSaveRequetDTO produtoDTO,int quantidade) {

        Produto produto = transformarDTOemProduto(produtoDTO);
        if (produtoRepository.existsByNome(produto.getNome())) {
            throw new ProdutoExistenteException("Produto já cadastrado");
        }
        Produto produtoSalvo = produtoRepository.save(produto);

        Estoque estoque = new Estoque();
        estoque.setProduto(produtoSalvo);
        estoque.setQuantidadeEstoque(quantidade);
        produtoSalvo.setEstoque(estoque);
        estoqueRepository.save(estoque);

        return transformarEmResponseDTO(produtoSalvo);

    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream().map(this::transformarEmResponseDTO).toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontradoExeption("Produto não encontrado")
        );
        return transformarEmResponseDTO(produto);
    }

    public ProdutoResponseDTO atualizarParaInativo(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoExeption("Produto não encontrado"));

        if (produto.getStatusProduto()== StatusProduto.INATIVO){
            throw new StatusInvalidoException("Produto com status inativo");
        }

        produto.setStatusProduto(StatusProduto.INATIVO);
        Produto salvo = produtoRepository.save(produto);
        return transformarEmResponseDTO(salvo);
    }
    public ProdutoResponseDTO atualizarParaAtivo(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNaoEncontradoExeption("Produto não encontrado"));

        if (produto.getStatusProduto()== StatusProduto.ATIVO){
            throw new StatusInvalidoException("Produto com status ativo");
        }

        produto.setStatusProduto(StatusProduto.ATIVO);

        Produto salvo = produtoRepository.save(produto);
        return transformarEmResponseDTO(salvo);
    }


    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoDTO> buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findAllByNomeContainingIgnoreCase(nome);
        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoExeption("Nenhum produto encontrado com o nome: " + nome);
        }
        return produtos.stream().map(ProdutoDTO::new).toList();
    }


}
