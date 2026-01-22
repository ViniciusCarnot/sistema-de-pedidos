package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import com.vinicarnot.sistema_de_pedidos.entities.Telefone;
import com.vinicarnot.sistema_de_pedidos.entities.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAtualizarDadosDTO {

    private Long id;

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'Email' é obrigatório.")
    private String email;

    @NotBlank(message = "O campo 'CPF OU CNPJ' é obrigatório.")
    private String cpfOuCnpj;

    @NotNull(message = "O campo 'Tipo de Pessoa' é obrigatório.")
    private TipoCliente tipo;

    @NotEmpty(message = "O campo 'Telefones' deve ter pelo menos 1 telefone.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 telefones.")
    private Set<TelefoneDTO> telefones = new HashSet<>();

    @NotEmpty(message = "O campo 'Endereços' deve ter pelo menos 1 endereço.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 endereços.")
    private Set<EnderecoDTO> enderecos = new HashSet<>();

    public ClienteAtualizarDadosDTO(Cliente entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        cpfOuCnpj = entity.getCpfOuCnpj();
        tipo = entity.getTipo();
        for(Telefone telefone : entity.getTelefones()) {
            telefones.add(new TelefoneDTO(telefone));
        }
        for(Endereco endereco: entity.getEnderecos()) {
            enderecos.add(new EnderecoDTO(endereco));
        }
    }

}
