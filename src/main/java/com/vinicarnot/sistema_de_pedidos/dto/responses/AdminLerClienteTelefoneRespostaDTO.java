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
public class AdminLerClienteTelefoneRespostaDTO {

    private Long id;

    private String numero;

    public AdminLerClienteTelefoneRespostaDTO(Telefone telefone) {
        id = telefone.getId();
        numero = telefone.getNumero();
    }

}
