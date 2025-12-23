package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemPedidoDTO {

    private Long produtoId;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;

    public ItemPedidoDTO(ItemPedido entity) {
        produtoId = entity.getProduto().getId();
        nome = entity.getProduto().getNome();
        preco = entity.getPreco();
        quantidade = entity.getQuantidade();
    }

    public BigDecimal getSubTotal() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

}
