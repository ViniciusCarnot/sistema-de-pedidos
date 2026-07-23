package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoItemPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ItemPedidoService {

    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /*
    @Transactional(rollbackFor = Exception.class)
    public void adicionarItemPedido(Pedido pedido, CriarPedidoRequisicaoDTO dtoRequest) {
        for(CriarPedidoItemPedidoRequisicaoDTO createItemPedidoRequestDTO : dtoRequest.getItems()) {
            Produto produto = produtoRepository.getReferenceById(createItemPedidoRequestDTO.getIdProduto());
            if(produto.getDisponibilidade().equals(Disponibilidade.INDISPONIVEL)) {
                throw new RecursoNaoEncontradoException("Produto com o id: " + createItemPedidoRequestDTO.getIdProduto() +
                " não foi encontrado.");
            }
            ItemPedido itemPedido = new ItemPedido(produto,
                    pedido,
                    createItemPedidoRequestDTO.getQuantidade(),
                    calculoDescontoItemPedido(produto.getPreco(), createItemPedidoRequestDTO.getPrecoPago())
            );
            pedido.getItemsPedidos().add(itemPedido);
        }
    }

    public BigDecimal calculoDescontoItemPedido(BigDecimal precoNormalProduto, BigDecimal precoPago) {
        return precoNormalProduto.subtract(precoPago);
    }

     */

}
