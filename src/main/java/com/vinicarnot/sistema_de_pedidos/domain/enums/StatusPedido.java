package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {

    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGAMENTO_APROVADO("Pagamento Aprovado"),
    EM_SEPARACAO("Pedido em separação"),
    PRONTO_PARA_ENVIO("Pedido pronto para envio"),
    DESPACHADO("Pedido despachado"),
    EM_ROTA_DE_ENTREGA("Pedido em rota de entrega"),
    ENTREGUE("Pedido entregue"),
    DEVOLVIDO("Pedido devolvido"),
    REEMBOLSADO("Pedido reembolsado"),
    CANCELADO("Pedido cancelado");

    private String status;

    StatusPedido(String status) {
        this.status = status;
    }

}
