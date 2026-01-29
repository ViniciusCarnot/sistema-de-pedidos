package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnderecoRequestDTO {

    @NotBlank(message = "O campo 'Logradouro' é obrigatório.")
    private String logradouro;

    @NotBlank(message = "O campo 'Número' é obrigatório.")
    private String numero;

    private String bairro;

    @NotNull(message = "O campo 'Cidade' é obrigatório.")
    private CreateCidadeRequestDTO cidade;

    @NotNull(message = "O campo 'Estado' é obrigatório.")
    private CreateEstadoRequestDTO estado;

}
