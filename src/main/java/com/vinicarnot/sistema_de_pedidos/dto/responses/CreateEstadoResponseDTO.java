package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEstadoResponseDTO {

    private Long id;
    private String nome;

    public CreateEstadoResponseDTO(Estado estado) {
        id = estado.getId();
        nome = estado.getNome();
    }

}
