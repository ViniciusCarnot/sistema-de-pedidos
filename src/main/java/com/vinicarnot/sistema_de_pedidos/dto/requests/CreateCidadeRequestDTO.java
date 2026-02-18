package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCidadeRequestDTO {

    @NotNull(message = "O campo 'id' da Cidade é obrigatório.")
    private Long id;

}
