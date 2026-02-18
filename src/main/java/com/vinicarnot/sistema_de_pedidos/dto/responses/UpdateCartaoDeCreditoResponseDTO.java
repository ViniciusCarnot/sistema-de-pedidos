package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCartaoDeCreditoResponseDTO extends UpdatePagamentoResponseDTO {

    private Integer quantidadeDeParcelas;

    public UpdateCartaoDeCreditoResponseDTO(CartaoDeCredito cartaoDeCredito) {
        setEstadoPagamento(cartaoDeCredito.getEstadoPagamento().getEstadoAtual());
        setTipoPagamento(cartaoDeCredito.getTipoPagamento().getTipo());
        quantidadeDeParcelas = cartaoDeCredito.getQuantidadeParcelas();
    }

}
