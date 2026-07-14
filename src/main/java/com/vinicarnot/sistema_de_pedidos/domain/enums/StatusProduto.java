package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum StatusProduto {

    DISPONIVEL("Disponivel"),
    INDISPONIVEL("Indisponivel");

    private String status;

    StatusProduto(String status) {
        this.status = status;
    }

}
