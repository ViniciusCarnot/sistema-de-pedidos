package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum Disponibilidade {

    DISPONIVEL("Disponivel"),
    INDISPONIVEL("Indisponivel");

    private String status;

    Disponibilidade(String status) {
        this.status = status;
    }

}
