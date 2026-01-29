package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemPedidoResponseDTO {

    private String nomeProduto;
    private BigDecimal preco;
    private BigDecimal desconto;
    private Integer quantidade;

    public CreateItemPedidoResponseDTO(ItemPedido itemPedido) {
        nomeProduto = itemPedido.getProduto().getNome();
        preco = itemPedido.getProduto().getPreco();
        desconto = itemPedido.getDesconto();
        quantidade = itemPedido.getQuantidade();
    }

    @JsonProperty("subTotal")
    public BigDecimal getSubTotal() {
        return (preco.subtract(desconto)).multiply(BigDecimal.valueOf(quantidade));
    }

}
