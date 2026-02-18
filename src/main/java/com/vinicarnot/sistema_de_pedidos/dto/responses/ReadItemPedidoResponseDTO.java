package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadItemPedidoResponseDTO {

    private Long idProduto;
    private String nomeProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    private BigDecimal descontoUnitario;

    public ReadItemPedidoResponseDTO(ItemPedido itemPedido) {
        idProduto = itemPedido.getProduto().getId();
        nomeProduto = itemPedido.getProduto().getNome();
        precoUnitario = itemPedido.getProduto().getPreco();
        quantidade = itemPedido.getQuantidade();
        descontoUnitario = itemPedido.getDescontoUnitario();
    }

    @JsonProperty("descontoTotalItem")
    public BigDecimal getDescontoTotal() {
        return descontoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @JsonProperty("subTotalItem")
    public BigDecimal getSubTotal() {
        return (precoUnitario.multiply(BigDecimal.valueOf(quantidade))).subtract(getDescontoTotal());
    }

}
