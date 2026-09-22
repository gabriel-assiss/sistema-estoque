package com.projeto.estoque.service;

import com.projeto.estoque.dto.compra.CompraRequestDTO;
import com.projeto.estoque.dto.compra.ItemCompraRequestDTO;
import com.projeto.estoque.entity.*;
import com.projeto.estoque.enums.StatusCompra;
import com.projeto.estoque.enums.TipoMovimentacao;
import com.projeto.estoque.exception.*;
import com.projeto.estoque.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final ItemCompraRepository itemCompraRepository;
    public CompraService(CompraRepository compraRepository,
                         ProdutoRepository produtoRepository,
                         FornecedorRepository fornecedorRepository,
                         FuncionarioRepository funcionarioRepository,
                         MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
                         ItemCompraRepository itemCompraRepository) {
        this.compraRepository = compraRepository;
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.itemCompraRepository = itemCompraRepository;
    }

    @Transactional
    public Compra salvar(CompraRequestDTO compraDTO) {
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());

        compra.setFornecedor(fornecedorRepository.findById(compraDTO.getFornecedor()).orElseThrow(
                () -> new FornecedorNaoEncontradoException("Fornecedor Não encontrado")
        ));
        compra.setFuncionario(funcionarioRepository.findById(compraDTO.getFuncionario()).orElseThrow(
                () -> new FuncionarioNaoEncontradoException("Funcionario não  encontrado")
        ));

        for (ItemCompraRequestDTO itemRequest : compraDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId()).orElseThrow(
                    () -> new ProdutoNaoEncontradoExeption("Produto não encontrado")
            );

            produto.getEstoque().setQuantidadeEstoque(produto.getEstoque().getQuantidadeEstoque() + itemRequest.getQuantidade());


            ItemCompra itemCompra = new ItemCompra();
            itemCompra.setProduto(produto);
            itemCompra.setQuantidade(itemRequest.getQuantidade());
            itemCompra.setCompra(compra);
            compra.getItensCompra().add(itemCompra);

            MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
            movimentacaoEstoque.setEstoque(produto.getEstoque());
            movimentacaoEstoque.setData(LocalDate.now());
            movimentacaoEstoque.setFuncionario(compra.getFuncionario());
            movimentacaoEstoque.setQuantidade(itemRequest.getQuantidade());
            movimentacaoEstoque.setTipoDeMovimentacao(TipoMovimentacao.ENTRADA);
            movimentacaoEstoqueRepository.save(movimentacaoEstoque);

        }
        compra.setStatusCompra(StatusCompra.CONCLUIDA);

        return compraRepository.save(compra);
    }


    @Transactional
    public Compra cancelar(Long idCompra, Long  idFuncionario ) {
        Compra compraExistente = compraRepository.findById(idCompra).orElseThrow(
                ()-> new CompraNaoEncontradaException("Compra não encontrada")
        );

        StatusCompra statusAnterior = compraExistente.getStatusCompra();
        StatusCompra novoStatus = StatusCompra.CANCELADA;


        Funcionario funcionario=funcionarioRepository.findById(idFuncionario).orElseThrow(
                () -> new FuncionarioNaoEncontradoException("Funcionario não encontrado")
        );

        if (statusAnterior != StatusCompra.CANCELADA
                && novoStatus == StatusCompra.CANCELADA) {



            List<ItemCompra> itens = itemCompraRepository.findByCompra(compraExistente);

                for (ItemCompra itemCompra : itens) {
                    Produto produto = itemCompra.getProduto();
                    if (produto.getEstoque().getQuantidadeEstoque() < itemCompra.getQuantidade()) {
                        throw new EstoqueInsuficienteException(
                                "Estoque insuficiente para cancelar a compra"
                        );
                    }
                        produto.getEstoque().setQuantidadeEstoque(produto.getEstoque().getQuantidadeEstoque() - itemCompra.getQuantidade());
                    MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
                    movimentacaoEstoque.setEstoque(produto.getEstoque());
                    movimentacaoEstoque.setData(LocalDate.now());
                    movimentacaoEstoque.setFuncionario(funcionario);
                    movimentacaoEstoque.setQuantidade(itemCompra.getQuantidade());
                    movimentacaoEstoque.setTipoDeMovimentacao(TipoMovimentacao.MUDANCA);
                    movimentacaoEstoqueRepository.save(movimentacaoEstoque);
                }

        }else{
            throw new StatusInvalidoException("Status Inválido");
        }
        compraExistente.setStatusCompra(novoStatus);

        return compraRepository.save(compraExistente);

    }

    public List<Compra> listarTodos() {
        return compraRepository.findAll();
    }

    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }
    public Optional<List<Compra>> buscarPorFuncionario(Long id) {
        Funcionario funcionario  = funcionarioRepository.findById(id).orElseThrow(
                ()-> new FuncionarioNaoEncontradoException("Funcionario nao encontrado")
        );


        return compraRepository.findByFuncionario(funcionario);
    }

    public  Optional<List<Compra>> buscarPorFornecedor(Long id) {
        Fornecedor fornecedor =  fornecedorRepository.findById(id).orElseThrow(
                ()-> new FornecedorNaoEncontradoException("Fornecedor nao encontrado")
        );
        return compraRepository.findByFornecedor(fornecedor);
    }



}
