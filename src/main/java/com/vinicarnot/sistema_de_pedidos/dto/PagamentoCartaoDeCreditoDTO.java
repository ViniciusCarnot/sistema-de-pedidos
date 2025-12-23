package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PagamentoCartaoDeCreditoDTO extends PagamentoDTO {

    private Integer quantidadeDeParcelas;

}
