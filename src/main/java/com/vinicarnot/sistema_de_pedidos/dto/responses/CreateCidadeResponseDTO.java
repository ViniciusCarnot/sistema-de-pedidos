package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCidadeResponseDTO {

    private String nome;

    public CreateCidadeResponseDTO(Cidade cidade) {
        nome = cidade.getNome();
    }

}
