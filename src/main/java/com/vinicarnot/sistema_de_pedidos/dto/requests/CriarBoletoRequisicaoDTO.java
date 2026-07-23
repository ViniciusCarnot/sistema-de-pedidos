package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CriarBoletoRequisicaoDTO {

    @NotNull(message = "O campo 'dataDeValidade' é obrigatório.")
    private LocalDate dataDeValidade;

}
