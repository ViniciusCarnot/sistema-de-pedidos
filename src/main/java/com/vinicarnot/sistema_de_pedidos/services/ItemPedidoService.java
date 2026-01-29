package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateItemPedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.entities.Pedido;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class ItemPedidoService {

    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void adicionarItemPedido(Pedido pedido, Set<CreateItemPedidoRequestDTO> createItemPedidoRequestDTOSet) {
        for(CreateItemPedidoRequestDTO createItemPedidoRequestDTO : createItemPedidoRequestDTOSet) {
            Produto produto = produtoRepository.getReferenceById(createItemPedidoRequestDTO.getId());
            ItemPedido itemPedido = new ItemPedido(produto,
                    pedido,
                    createItemPedidoRequestDTO.getQuantidade(),
                    produto.getPreco(),
                    calculoDescontoItemPedido(produto.getPreco(), createItemPedidoRequestDTO.getPreco())
            );
            pedido.getItemsPedidos().add(itemPedido);
        }
    }

    public BigDecimal calculoDescontoItemPedido(BigDecimal precoNormalProduto, BigDecimal precoVendido) {
        return precoNormalProduto.subtract(precoVendido);
    }

}
