package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Cidade;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
