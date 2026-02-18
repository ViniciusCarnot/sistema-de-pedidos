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
public class CreatePedidoResponseDTO {

    @Setter
    private Long id;

    @Setter
    private Instant instanteDaCompra;

    private Set<CreateItemPedidoResponseDTO> items = new HashSet<>();

    @Setter
    private CreateEnderecoResponseDTO enderecoDeEntrega;

    @Setter
    private CreatePagamentoResponseDTO pagamento;

    public CreatePedidoResponseDTO(Pedido pedido) {
        id = pedido.getId();
        instanteDaCompra = pedido.getInstanteDaCompra();
        for(ItemPedido itemPedido : pedido.getItemsPedidos()) {
            items.add(new CreateItemPedidoResponseDTO(itemPedido));
        }
        enderecoDeEntrega = new CreateEnderecoResponseDTO(pedido.getEnderecoDeEntrega());
        if(pedido.getPagamento() instanceof Boleto boleto) {
            pagamento = new CreateBoletoResponseDTO(boleto);
        } else if (pedido.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            pagamento = new CreateCartaoDeCreditoResponseDTO(cartaoDeCredito);
        }
    }

    @JsonProperty("valorTotalPedido")
    public BigDecimal getValorTotal() {
        return items.stream()
                .map(item -> item.getSubTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
