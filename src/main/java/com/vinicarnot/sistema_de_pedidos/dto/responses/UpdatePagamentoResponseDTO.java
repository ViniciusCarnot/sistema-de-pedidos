package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.entities.TipoPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class UpdatePagamentoResponseDTO {

    private EstadoPagamento estadoPagamento;
    private TipoPagamento tipoPagamento;

}
