package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CriarBoletoRequisicaoDTO extends CriarPagamentoRequisicaoDTO {

    @NotBlank(message = "O campo 'codigoDeBarras' é obrigatório.")
    private String codigoDeBarras;

    @NotNull(message = "O campo 'dataDeValidade' é obrigatório.")
    private LocalDate dataDeValidade;

}
