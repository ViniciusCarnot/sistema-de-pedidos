package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Boleto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBoletoResponseDTO extends UpdatePagamentoResponseDTO {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    public UpdateBoletoResponseDTO(Boleto boleto) {
        setEstadoPagamento(boleto.getEstadoPagamento());
        setTipoPagamento(boleto.getTipoPagamento());
        dataVencimento = (boleto.getDataVencimento());
        dataPagamento = (boleto.getDataVencimento());
    }

}
