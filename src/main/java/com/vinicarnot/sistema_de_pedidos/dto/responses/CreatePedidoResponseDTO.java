package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Boleto;
import com.vinicarnot.sistema_de_pedidos.entities.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.entities.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CreatePedidoResponseDTO {

    @Setter
    private Long pedidoId;

    private Set<CreateItemPedidoResponseDTO> items = new HashSet<>();

    @Setter
    private CreateEnderecoResponseDTO enderecoDeEntrega;

    @Setter
    private CreatePagamentoResponseDTO pagamento;

    public CreatePedidoResponseDTO(Pedido pedido) {
        pedidoId = pedido.getId();
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


}
