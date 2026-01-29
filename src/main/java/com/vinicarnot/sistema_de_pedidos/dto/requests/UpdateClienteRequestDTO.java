package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.dto.EnderecoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.TelefoneDTO;
import com.vinicarnot.sistema_de_pedidos.entities.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UpdateClienteRequestDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'Email' é obrigatório.")
    private String email;

    @NotBlank(message = "O campo 'CPF OU CNPJ' é obrigatório.")
    private String cpfOuCnpj;

    @NotNull(message = "O campo 'Tipo de Pessoa' é obrigatório.")
    private TipoCliente tipoPessoa;

    @NotEmpty(message = "O campo 'Telefones' deve ter pelo menos 1 telefone.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 telefones.")
    private Set<UpdateTelefoneRequestDTO> telefones = new HashSet<>();

    @NotEmpty(message = "O campo 'Endereços' deve ter pelo menos 1 endereço.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 endereços.")
    private Set<UpdateEnderecoRequestDTO> enderecos = new HashSet<>();

}
