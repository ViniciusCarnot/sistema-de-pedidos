package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LerCategoriaRespostaDTO {

    private Long id;

    private String nome;

    public LerCategoriaRespostaDTO (Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
    }

}
