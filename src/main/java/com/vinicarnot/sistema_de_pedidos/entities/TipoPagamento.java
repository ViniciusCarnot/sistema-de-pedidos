package com.vinicarnot.sistema_de_pedidos.entities;

import lombok.Getter;

@Getter
public enum TipoPagamento {

    BOLETO("BOLETO"),
    CARTAO_DE_CREDITO("CARTAO_DE_CREDITO");

    private String tipo;

    TipoPagamento(String tipo) {
        this.tipo = tipo;
    }
}
