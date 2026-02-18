package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    private String tipo;

    TipoCliente(String tipo) {
        this.tipo = tipo;
    }
}
