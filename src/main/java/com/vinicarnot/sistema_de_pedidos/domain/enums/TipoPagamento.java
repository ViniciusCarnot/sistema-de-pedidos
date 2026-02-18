package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum TipoPagamento {

    BOLETO("Boleto"),
    CARTAO_DE_CREDITO("Cartão de Crédito");

    private String tipo;

    TipoPagamento(String tipo) {
        this.tipo = tipo;
    }
}
