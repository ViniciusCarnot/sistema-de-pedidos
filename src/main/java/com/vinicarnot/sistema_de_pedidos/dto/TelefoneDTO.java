package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TelefoneDTO {

    private Long id;

    @NotBlank(message = "O campo 'Número' é obrigatório.")
    private String numero;

    public TelefoneDTO(Telefone entity) {
        id = entity.getId();
        numero = entity.getNumero();
    }

}
