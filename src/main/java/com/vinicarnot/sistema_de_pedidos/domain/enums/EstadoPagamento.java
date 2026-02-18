package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoPagamento {

    PENDENTE("Pendente"),
    QUITADO("Quitado"),
    CANCELADO("Cancelado");

    private String estadoAtual;

    EstadoPagamento(String estadoAtual) {
        this.estadoAtual = estadoAtual;
    }
}
