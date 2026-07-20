package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarMinhaContaRequisicaoDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'email' é obrigatório.")
    @Email(message = "O campo 'email' deve ser válido.")
    private String email;

    @NotBlank(message = "O campo 'senha' é obrigatório.")
    private String senha;

    @NotBlank(message = "O campo 'cpfOuCnpj' é obrigatório.")
    @Size(min = 14, max = 18, message = "O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres.")
    private String cpfOuCnpj;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    private TipoCliente tipo;

    @NotNull(message = "O campo 'telefone' não pode ser nulo.")
    private AtualizarTelefoneRequisicaoDTO telefone;

}
