package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TelefoneDTO {

    private Long id;

    private String numero;

    public TelefoneDTO(Telefone entity) {
        id = entity.getId();
        numero = entity.getNumero();
    }

}
