package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarMeuEnderecoRequisicaoDTO {

    @NotBlank(message = "O campo 'logradouro' é obrigatório.")
    private String logradouro;

    @NotBlank(message = "O campo 'numero' é obrigatório.")
    private String numero;

    @NotBlank(message = "O campo 'bairro' é obrigatório.")
    private String bairro;

    @NotNull(message = "O campo 'cidadeId' é obrigatório.")
    private Long cidadeId;

    @NotNull(message = "O campo 'estadoId' é obrigatório.")
    private Long estadoId;

}
