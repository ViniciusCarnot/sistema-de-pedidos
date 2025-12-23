package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.EstadoPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public abstract class PagamentoDTO {

    private Long id;
    private EstadoPagamento estado;
}
