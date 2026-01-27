package com.vinicarnot.sistema_de_pedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PagamentoCartaoDeCreditoDTO extends PagamentoDTO {

    @NotNull(message = "O campo 'Quantidade de Parcelas' é obrigatório.")
    private Integer quantidadeDeParcelas;

}
