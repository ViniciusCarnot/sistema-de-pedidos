package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class LerPedidoRespostaDTO {

    @Setter
    private Long pedidoId;

    @Setter
    private String compradorNome;

    @Setter
    private Instant dataDeEmissao;

    @Setter
    private StatusPedido pedidoStatus;

    private Set<LerPedidoItemPedidoRespostaDTO> items = new HashSet<>();

    @Setter
    private LerPedidoEnderecoRespostaDTO enderecoDeEntrega;

    public LerPedidoRespostaDTO(Pedido pedido) {
        pedidoId = pedido.getId();
        compradorNome = pedido.getCliente().getNome();
        dataDeEmissao = pedido.getInstanteDaCompra();
        pedidoStatus = pedido.getStatusPedido();
        for(ItemPedido itemPedido : pedido.getItemsPedidos()) {
            items.add(new LerPedidoItemPedidoRespostaDTO(itemPedido));
        }
        enderecoDeEntrega = new LerPedidoEnderecoRespostaDTO(pedido.getEnderecoDeEntrega());
    }

    @JsonProperty("valorTotal")
    public BigDecimal calculoValorTotal() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for(LerPedidoItemPedidoRespostaDTO itemPedidoRespostaDTO : items) {
            valorTotal = valorTotal.add(itemPedidoRespostaDTO.getSubTotal());
        }
        return valorTotal;
    }

}
