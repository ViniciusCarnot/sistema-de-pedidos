package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CriarCadastroClienteRequisicaoDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    @Setter
    private String nome;

    @Email(message = "O campo 'Email' deve ser válido.")
    @NotBlank(message = "O campo 'Email' é obrigatório.")
    @Setter
    private String email;

    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    private String senha;

    @NotBlank(message = "O campo 'cpfOuCnpj' é obrigatório.")
    @Size(min = 14, max = 18, message = "O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres.")
    private String cpfOuCnpj;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    private TipoCliente tipo;

    @NotNull(message = "O campo 'telefone' não pode ser nulo.")
    private CriarCadastroClienteTelefoneRequisicaoDTO telefone;


}
