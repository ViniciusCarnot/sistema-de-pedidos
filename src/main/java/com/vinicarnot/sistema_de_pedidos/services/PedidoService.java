package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.*;
import com.vinicarnot.sistema_de_pedidos.entities.*;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;

    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
    }

    public PedidoDTO realizarPedido(PedidoDTO pedidoDTO) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.getReferenceById(clienteLogado.getId());
        //instanteDaCompra;pagamento;cliente;enderecoDeEntrega;itemsPedidos

        Pedido pedido = new Pedido();
        pedido.setInstanteDaCompra(Instant.now());
        pedido.setCliente(cliente);

        Cidade cidade = cidadeRepository.findById(pedidoDTO.getEnderecoDeEntrega().getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade não encontrada."));

        Endereco endereco = enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidade(pedidoDTO.getEnderecoDeEntrega().getLogradouro(),
                        pedidoDTO.getEnderecoDeEntrega().getNumero(), pedidoDTO.getEnderecoDeEntrega().getBairro(), cidade).
                orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(pedidoDTO.getEnderecoDeEntrega().getLogradouro());
                    novoEndereco.setNumero(pedidoDTO.getEnderecoDeEntrega().getNumero());
                    novoEndereco.setBairro(pedidoDTO.getEnderecoDeEntrega().getBairro());
                    novoEndereco.setCidade(cidade);
                    novoEndereco.getClientes().add(cliente);
                    return enderecoRepository.save(novoEndereco);
                });

        cliente.getEnderecos().add(endereco);

        pedido.setEnderecoDeEntrega(endereco);

        for(ItemPedidoDTO itemPedidoDTO : pedidoDTO.getItems()) {
            Produto produto = produtoRepository.getReferenceById(itemPedidoDTO.getProdutoId());
            ItemPedido itemPedido = new ItemPedido(produto,
                    pedido,
                    itemPedidoDTO.getQuantidade(),
                    produto.getPreco(),
                    calculoDescontoItemPedido(produto, itemPedidoDTO)
            );
            pedido.getItemsPedidos().add(itemPedido);
        }

        Pagamento pagamento = criarFormaDePagamento(pedidoDTO.getPagamento(), pedido);
        pedido.setPagamento(pagamento);

        return new PedidoDTO(pedidoRepository.save(pedido));
    }

    public Pagamento criarFormaDePagamento(PagamentoDTO pagamentoDTO, Pedido pedido) {
        if(pagamentoDTO instanceof PagamentoBoletoDTO pagamentoBoletoDTO) {
            Boleto boleto = new Boleto();
            boleto.setTipoPagamento(pagamentoBoletoDTO.getTipoPagamento());
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setDataVencimento(LocalDate.now().plusMonths(3));
            boleto.setPedido(pedido);
            return boleto;
        } else if(pagamentoDTO instanceof PagamentoCartaoDeCreditoDTO pagamentoCartaoDeCreditoDTO) {
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setTipoPagamento(pagamentoCartaoDeCreditoDTO.getTipoPagamento());
            cartaoDeCredito.setEstadoPagamento(EstadoPagamento.PENDENTE);
            cartaoDeCredito.setQuantidadeParcelas(pagamentoCartaoDeCreditoDTO.getQuantidadeDeParcelas());
            cartaoDeCredito.setPedido(pedido);
            return cartaoDeCredito;
        } else {
            throw new RecursoNaoEncontradoException("Forma de Pagamento inválida.");
        }
    }

    public BigDecimal calculoDescontoItemPedido(Produto produto, ItemPedidoDTO itemPedidoDTO) {
        BigDecimal precoNormalProduto = produto.getPreco();
        BigDecimal precoVendido = itemPedidoDTO.getPreco();
        return precoNormalProduto.subtract(precoVendido);
    }

}
