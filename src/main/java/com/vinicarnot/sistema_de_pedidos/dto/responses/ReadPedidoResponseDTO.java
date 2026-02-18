package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ReadPedidoResponseDTO {

    @Setter
    private Long id;

    @Setter
    private Instant instanteDaCompra;

    @Setter
    private String statusPedido;

    private Set<ReadItemPedidoResponseDTO> items = new HashSet<>();

    @Setter
    private ReadEnderecoResponseDTO enderecoDeEntrega;

    @Setter
    private ReadPagamentoResponseDTO pagamento;

    public ReadPedidoResponseDTO(Pedido pedido) {
        id = pedido.getId();
        instanteDaCompra = pedido.getInstanteDaCompra();
        statusPedido = pedido.getStatusPedido().getStatus();
        for(ItemPedido itemPedido : pedido.getItemsPedidos()) {
            items.add(new ReadItemPedidoResponseDTO(itemPedido));
        }
        enderecoDeEntrega = new ReadEnderecoResponseDTO(pedido.getEnderecoDeEntrega());
        if(pedido.getPagamento() instanceof Boleto boleto) {
            pagamento = new ReadBoletoResponseDTO(boleto);
        } else if (pedido.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            pagamento = new ReadCartaoDeCreditoResponseDTO(cartaoDeCredito);
        }
    }

    @JsonProperty("valorTotalPedido")
    public BigDecimal getValorTotal() {
        return items.stream()
                .map(item -> item.getSubTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
