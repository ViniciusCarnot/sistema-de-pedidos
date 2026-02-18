package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateClienteRequestDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'Email' é obrigatório.")
    private String email;

    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    private String senha;

}
