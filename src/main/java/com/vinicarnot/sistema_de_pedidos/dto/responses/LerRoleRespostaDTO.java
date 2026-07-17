package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LerRoleRespostaDTO {

    private Long id;

    private String nome;

    public LerRoleRespostaDTO(Role role) {
        id = role.getId();
        nome = role.getAuthority();
    }

}
