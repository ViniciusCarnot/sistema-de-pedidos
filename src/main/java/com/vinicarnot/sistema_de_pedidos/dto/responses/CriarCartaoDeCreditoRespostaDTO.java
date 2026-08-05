package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CriarCartaoDeCreditoRespostaDTO extends CriarPagamentoRespostaDTO {

    private Integer quantidadeParcelas;

    public CriarCartaoDeCreditoRespostaDTO(CartaoDeCredito cartaoDeCredito) {
        super(cartaoDeCredito.getId(), cartaoDeCredito.getTipoPagamento(), cartaoDeCredito.getEstadoPagamento());
        this.quantidadeParcelas = cartaoDeCredito.getQuantidadeParcelas();
    }
}
