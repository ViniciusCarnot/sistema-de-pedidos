package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;

public record LerRoleRespostaDTO(String nome) {

    public LerRoleRespostaDTO LerRoleRespostaDTO(Role role) {
        return new LerRoleRespostaDTO(role.getAuthority());
    }

}
