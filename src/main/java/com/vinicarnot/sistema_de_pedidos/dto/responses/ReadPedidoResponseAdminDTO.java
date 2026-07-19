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
public class ReadPedidoResponseAdminDTO {

    @Setter
    private Long id;

    @Setter
    private Instant instanteDaCompra;

    @Setter
    private String statusPedido;

    private Set<ReadItemPedidoResponseAdminDTO> items = new HashSet<>();

    @Setter
    private AdminLerEnderecoRespostaDTO enderecoDeEntrega;

    @Setter
    private ReadPagamentoResponseAdminDTO pagamento;

    @Setter
    private ReadClienteResponseAdminDTO cliente;

    public ReadPedidoResponseAdminDTO(Pedido pedido) {
        id = pedido.getId();
        instanteDaCompra = pedido.getInstanteDaCompra();
        statusPedido = pedido.getStatusPedido().getStatus();
        for(ItemPedido itemPedido : pedido.getItemsPedidos()) {
            items.add(new ReadItemPedidoResponseAdminDTO(itemPedido));
        }
        enderecoDeEntrega = new AdminLerEnderecoRespostaDTO(pedido.getEnderecoDeEntrega());
        if(pedido.getPagamento() instanceof Boleto boleto) {
            pagamento = new ReadBoletoResponseAdminDTO(boleto);
        } else if (pedido.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            pagamento = new ReadCartaoDeCreditoResponseAdminDTO(cartaoDeCredito);
        }
        cliente = new ReadClienteResponseAdminDTO(pedido.getCliente());
    }

    @JsonProperty("valorTotalPedido")
    public BigDecimal getValorTotal() {
        return items.stream()
                .map(item -> item.getSubTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
