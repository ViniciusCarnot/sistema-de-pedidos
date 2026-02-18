package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
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

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'cpfjOuCnpj' é obrigatório.")
    private String cpfOuCnpj;

    @NotNull(message = "O campo 'tipoPessoa' é obrigatório.")
    private TipoCliente tipoPessoa;

    @NotEmpty(message = "A lista 'telefones' deve ter pelo menos 1 telefone.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 telefones por pessoa.")
    private Set<UpdateTelefoneRequestDTO> telefones = new HashSet<>();

    @NotEmpty(message = "A lista 'endereços' deve ter pelo menos 1 endereço.")
    @Size(max = 2, message = "É possível adicionar no máximo 2 endereços por pessoa.")
    private Set<UpdateEnderecoRequestDTO> enderecos = new HashSet<>();

}
