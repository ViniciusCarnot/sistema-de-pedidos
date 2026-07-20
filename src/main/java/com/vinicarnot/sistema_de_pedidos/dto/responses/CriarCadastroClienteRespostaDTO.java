package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CriarCadastroClienteRespostaDTO {

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

    private List<RoleResponseDTO> roles = new ArrayList<>();

    @Setter
    private CriarCadastroClienteTelefoneRespostaDTO telefone;

    public CriarCadastroClienteRespostaDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
        cpfOuCnpj = cliente.getCpfOuCnpj();
        tipo = cliente.getTipo();
        for(Role role : cliente.getRoles()) {
            roles.add(new RoleResponseDTO(role));
        }
        telefone = new CriarCadastroClienteTelefoneRespostaDTO(cliente.getTelefone());
    }

}
