package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateTelefoneRequestDTO {

    @NotBlank(message = "O campo 'Número' do Telefone é obrigatório.")
    private String numero;

}
