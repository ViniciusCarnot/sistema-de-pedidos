package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CidadeDTO {

    private Long id;
    private String nome;

    public CidadeDTO(Cidade entity) {
        id = entity.getId();
        nome = entity.getNome();
    }

}
