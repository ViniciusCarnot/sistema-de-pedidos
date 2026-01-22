package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ClienteRegistroDTO {

    @NotBlank(message = "Nome é um campo obrigatório.")
    private String nome;

    @NotBlank(message = "Email é um campo obrigatório.")
    private String email;

    @NotBlank(message = "Senha é um campo obrigatório.")
    private String senha;

    private UserRole role;

}
