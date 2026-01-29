package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Boleto;
import com.vinicarnot.sistema_de_pedidos.entities.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePedidoResponseDTO {

    private Long pedidoId;
    private UpdateEnderecoResponseDTO enderecoDeEntrega;
    private UpdatePagamentoResponseDTO pagamento;

    public UpdatePedidoResponseDTO(Pedido pedido) {
        pedidoId = pedido.getId();
        enderecoDeEntrega = new UpdateEnderecoResponseDTO(pedido.getEnderecoDeEntrega());
        if(pedido.getPagamento() instanceof Boleto boleto) {
            pagamento = new UpdateBoletoResponseDTO(boleto);
        } else if (pedido.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            pagamento = new UpdateCartaoDeCreditoResponseDTO(cartaoDeCredito);
        }
    }

}
