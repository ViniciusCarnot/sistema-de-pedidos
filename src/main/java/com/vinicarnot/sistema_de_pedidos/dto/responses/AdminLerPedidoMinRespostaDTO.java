package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminLerPedidoMinRespostaDTO {

    private Long pedidoId;

    private String compradorNome;

    private String compradorEmail;

    private Instant dataDeEmissao;

    private StatusPedido pedidoStatus;

    public AdminLerPedidoMinRespostaDTO(Pedido pedido) {
        pedidoId = pedido.getId();
        compradorNome = pedido.getCliente().getNome();
        compradorEmail = pedido.getCliente().getEmail();
        dataDeEmissao = pedido.getInstanteDaCompra();
        pedidoStatus = pedido.getStatusPedido();
    }

}
