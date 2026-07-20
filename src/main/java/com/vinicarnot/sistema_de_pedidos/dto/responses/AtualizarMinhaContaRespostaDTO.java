package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarMinhaContaRespostaDTO {

    private Long id;

    private String nome;

    private String email;

    private String cpfOuCnpj;

    private TipoCliente tipo;

    private AtualizarTelefoneRespostaDTO telefone;

    public AtualizarMinhaContaRespostaDTO(Cliente cliente) {
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
        cpfOuCnpj = cliente.getCpfOuCnpj();
        tipo = cliente.getTipo();
        telefone = new AtualizarTelefoneRespostaDTO(cliente.getTelefone());
    }

}
