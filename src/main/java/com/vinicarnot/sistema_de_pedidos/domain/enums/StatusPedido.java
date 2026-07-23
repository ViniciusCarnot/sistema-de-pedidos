package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {

    AGUARDANDO_PAGAMENTO,
    PAGAMENTO_APROVADO,
    EM_SEPARACAO,
    PRONTO_PARA_ENVIO,
    DESPACHADO,
    EM_ROTA_DE_ENTREGA,
    ENTREGUE,
    DEVOLVIDO,
    REEMBOLSADO,
    CANCELADO;

}
