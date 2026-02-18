package com.vinicarnot.sistema_de_pedidos.dto.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ReadPagamentoResponseDTO {

    private Long id;
    private String estadoPagamento;
    private String tipoPagamento;

}
