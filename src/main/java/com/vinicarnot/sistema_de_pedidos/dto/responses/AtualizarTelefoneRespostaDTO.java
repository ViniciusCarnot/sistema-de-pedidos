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
public class AtualizarTelefoneRespostaDTO {

    private String numero;

    public AtualizarTelefoneRespostaDTO(Telefone telefone) {
        numero = telefone.getNumero();
    }

}
