package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ClienteRegistroDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo 'Email' é obrigatório.")
    private String email;

    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    private String senha;

    @NotNull(message = "O campo 'Role' é obrigatório.")
    private UserRole role;

}
