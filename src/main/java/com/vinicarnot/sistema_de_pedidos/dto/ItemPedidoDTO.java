package com.vinicarnot.sistema_de_pedidos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemPedidoDTO {

    @NotNull(message = "O campo 'Produto Id' é obrigatório.")
    private Long produtoId;

    private String nome;

    @NotNull(message = "O campo 'Preço' é obrigatório.")
    private BigDecimal preco;

    @NotNull(message = "O campo 'Quantidade' é obrigatório.")
    private Integer quantidade;

    private BigDecimal desconto;

    public ItemPedidoDTO(ItemPedido entity) {
        produtoId = entity.getProduto().getId();
        nome = entity.getProduto().getNome();
        preco = entity.getPreco();
        quantidade = entity.getQuantidade();
        desconto = entity.getDesconto();
    }

    @JsonProperty("subTotal")
    public BigDecimal getSubTotal() {
        return (preco.subtract(desconto)).multiply(BigDecimal.valueOf(quantidade));
    }

}
