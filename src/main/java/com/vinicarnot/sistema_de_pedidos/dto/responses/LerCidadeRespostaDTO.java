package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LerCidadeRespostaDTO {

    private Long id;

    private String nome;

    public LerCidadeRespostaDTO(Cidade cidade) {
        id = cidade.getId();
        nome = cidade.getNome();
    }

}
