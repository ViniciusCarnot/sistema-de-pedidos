package com.vinicarnot.sistema_de_pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClienteLoginDTO {

    @NotBlank(message = "Email é um campo obrigatório.")
    private String email;

    @NotBlank(message = "Senha é um campo obrigatório.")
    private String senha;

}
