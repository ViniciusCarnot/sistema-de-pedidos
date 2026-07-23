package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoRespostaDTO {

    private Long produtoId;
    private String produtoNome;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public ItemPedidoRespostaDTO(ItemPedido itemPedido) {
        produtoId = itemPedido.getId().getProduto().getId(); // Pega apenas o ID (não dispara SELECT)
        produtoNome = itemPedido.getNomeProduto();
        precoUnitario = itemPedido.getPrecoUnitario();
        quantidade = itemPedido.getQuantidade();
    }

    @JsonProperty("subTotalItem")
    public BigDecimal getSubTotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}
