package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private List<RoleResponseDTO> roles = new ArrayList<>();

    public CreateClienteResponseDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
        for(Role role : cliente.getRoles()) {
            roles.add(new RoleResponseDTO(role));
        }
    }

}
