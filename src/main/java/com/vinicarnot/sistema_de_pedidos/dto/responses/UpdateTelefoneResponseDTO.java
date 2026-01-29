package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTelefoneResponseDTO {

    private String numero;

    public UpdateTelefoneResponseDTO(Telefone telefone) {
        numero = telefone.getNumero();
    }

}
