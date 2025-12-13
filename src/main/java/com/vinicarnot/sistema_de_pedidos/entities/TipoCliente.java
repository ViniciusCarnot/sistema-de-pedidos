package com.vinicarnot.sistema_de_pedidos.entities;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOA_FISICA("PESSOA_FISICA"),
    PESSOA_JURIDICA("PESSOA_JURIDICA");

    private String tipo;

    TipoCliente(String tipo) {
        this.tipo = tipo;
    }
}
