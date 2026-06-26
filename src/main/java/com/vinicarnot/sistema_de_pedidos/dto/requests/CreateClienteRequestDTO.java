package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CreateClienteRequestDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    @Setter
    private String nome;

    @Email(message = "O campo 'Email' deve ser válido.")
    @NotBlank(message = "O campo 'Email' é obrigatório.")
    @Setter
    private String email;

    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    @Setter
    private String senha;

}
