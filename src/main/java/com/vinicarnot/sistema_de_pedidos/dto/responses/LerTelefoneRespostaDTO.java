package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LerTelefoneRespostaDTO {

    private String numero;

    public LerTelefoneRespostaDTO(Telefone telefone) {
        numero = telefone.getNumero();
    }

}
