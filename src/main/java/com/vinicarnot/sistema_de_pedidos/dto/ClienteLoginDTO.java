package com.vinicarnot.sistema_de_pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClienteLoginDTO {

    @NotBlank(message = "O campo 'Email' é obrigatório.")
    private String email;

    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    private String senha;

}
