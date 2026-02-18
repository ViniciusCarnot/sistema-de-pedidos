package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateEnderecoRequestDTO {

    @NotBlank(message = "O campo 'logradouro' é obrigatório.")
    private String logradouro;

    @NotBlank(message = "O campo 'numero' é obrigatório.")
    private String numero;

    private String bairro;

    @NotNull(message = "O campo 'cidade' é obrigatório.")
    private UpdateCidadeRequestDTO cidade;

    @NotNull(message = "O campo 'estado' é obrigatório.")
    private UpdateEstadoRequestDTO estado;

}
