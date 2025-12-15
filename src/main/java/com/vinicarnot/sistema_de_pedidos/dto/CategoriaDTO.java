package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.Getter;

@Getter
public class CategoriaDTO {

    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(String nome) {
        this.nome = nome;
    }

}
