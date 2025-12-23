package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import com.vinicarnot.sistema_de_pedidos.entities.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private TipoCliente tipo;

    private Set<EnderecoDTO> enderecos = new HashSet<>();

    private Set<TelefoneDTO> telefones = new HashSet<>();

    public ClienteDTO(Long id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
    }

    public ClienteDTO(Cliente entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        cpfOuCnpj = entity.getCpfOuCnpj();
        tipo = entity.getTipo();
        for(Endereco endereco : entity.getEnderecos()) {
            enderecos.add(new EnderecoDTO(endereco));
        }
        for(Telefone telefone : entity.getTelefones()) {
            telefones.add(new TelefoneDTO(telefone));
        }
    }
}
