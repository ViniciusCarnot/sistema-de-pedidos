package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import com.vinicarnot.sistema_de_pedidos.entities.TipoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UpdateClienteResponseDTO {

    @Setter
    private String nome;

    @Setter
    private String email;

    @Setter
    private String cpfOuCnpj;

    @Setter
    private TipoCliente tipoPessoa;

    private Set<UpdateTelefoneResponseDTO> telefones = new HashSet<>();

    private Set<UpdateEnderecoResponseDTO> enderecos = new HashSet<>();

    public UpdateClienteResponseDTO(Cliente cliente) {
        nome = cliente.getNome();
        email = cliente.getEmail();
        cpfOuCnpj = cliente.getCpfOuCnpj();
        tipoPessoa = cliente.getTipo();
        for(Telefone telefone : cliente.getTelefones()) {
            telefones.add(new UpdateTelefoneResponseDTO(telefone));
        }
        for(Endereco endereco : cliente.getEnderecos()) {
            enderecos.add(new UpdateEnderecoResponseDTO(endereco));
        }
    }

}
