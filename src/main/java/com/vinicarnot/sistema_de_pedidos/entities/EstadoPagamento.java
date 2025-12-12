package com.vinicarnot.sistema_de_pedidos.entities;

public enum EstadoPagamento {

    PENDENTE("PENDENTE"),
    QUITADO("QUITADO"),
    CANCELADO("CANCELADO");

    private String estadoAtual;

    EstadoPagamento(String estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }
}
