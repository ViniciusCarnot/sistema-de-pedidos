package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCidadeResponseAdminDTO {

    private Long id;
    private String nome;

    public UpdateCidadeResponseAdminDTO(Cidade cidade) {
        id = cidade.getId();
        nome = cidade.getNome();
    }

}
