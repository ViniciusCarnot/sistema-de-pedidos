package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CriarCartaoDeCreditoRequisicaoDTO {

    @NotNull(message = "O campo 'quantidadeDeParcelas' é obrigatório.")
    @Min(value = 1, message = "O mínimo de parcelas deve ser 1.")
    private Integer quantidadeDeParacelas;

}
