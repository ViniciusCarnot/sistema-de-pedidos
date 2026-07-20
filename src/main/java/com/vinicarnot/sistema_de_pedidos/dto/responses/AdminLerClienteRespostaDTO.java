package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class AdminLerClienteRespostaDTO {

    @Setter
    private Long id;

    @Setter
    private String nome;

    @Setter
    private String email;

    @Setter
    private String cpfOuCnpj;

    @Setter
    private TipoCliente tipo;

    @Setter
    private boolean ativo;

    private Set<LerRoleRespostaDTO> roles = new HashSet<>();

    @Setter
    private AdminLerClienteTelefoneRespostaDTO telefone;

    public AdminLerClienteRespostaDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
        cpfOuCnpj = cliente.getCpfOuCnpj();
        tipo = cliente.getTipo();
        ativo = cliente.isEnabled();
        for(Role role : cliente.getRoles()) {
            roles.add(new LerRoleRespostaDTO(role));
        }
        telefone = new AdminLerClienteTelefoneRespostaDTO(cliente.getTelefone());
    }

}
