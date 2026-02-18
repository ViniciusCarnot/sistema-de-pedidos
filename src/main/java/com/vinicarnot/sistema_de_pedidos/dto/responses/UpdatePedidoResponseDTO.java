package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePedidoResponseDTO {

    private Long pedidoId;
    private Instant instanteDaCompra;
    private Set<UpdateItemPedidoResponseDTO> items = new HashSet<>();
    private UpdateEnderecoResponseDTO enderecoDeEntrega;
    private UpdatePagamentoResponseDTO pagamento;

    public UpdatePedidoResponseDTO(Pedido pedido) {
        pedidoId = pedido.getId();
        instanteDaCompra = pedido.getInstanteDaCompra();
        for(ItemPedido itemPedido : pedido.getItemsPedidos()) {
            items.add(new UpdateItemPedidoResponseDTO(itemPedido));
        }
        enderecoDeEntrega = new UpdateEnderecoResponseDTO(pedido.getEnderecoDeEntrega());
        if(pedido.getPagamento() instanceof Boleto boleto) {
            pagamento = new UpdateBoletoResponseDTO(boleto);
        } else if (pedido.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            pagamento = new UpdateCartaoDeCreditoResponseDTO(cartaoDeCredito);
        }
    }

    @JsonProperty("valorTotalPedido")
    public BigDecimal getValorTotal() {
        return items.stream()
                .map(item -> item.getSubTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @JsonProperty("instanteDaAtualizacao")
    public Instant getInstanteAtualizacao() {
        return Instant.now();
    }

}
