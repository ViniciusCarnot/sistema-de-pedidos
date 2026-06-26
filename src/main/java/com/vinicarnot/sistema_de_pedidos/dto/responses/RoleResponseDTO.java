package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import lombok.*;

@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoleResponseDTO {

    private Long id;
    private String nome;

    public RoleResponseDTO(Role role) {
        id = role.getId();
        nome = role.getAuthority();
    }

}
