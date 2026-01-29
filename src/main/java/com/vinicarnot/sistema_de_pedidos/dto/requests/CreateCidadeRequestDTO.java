package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCidadeRequestDTO {

    @NotNull(message = "O campo 'Id' da Cidade é obrigatório.")
    private Long id;

}
