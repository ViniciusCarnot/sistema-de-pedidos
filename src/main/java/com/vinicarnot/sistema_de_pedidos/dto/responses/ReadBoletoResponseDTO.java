package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReadBoletoResponseDTO extends ReadPagamentoResponseDTO {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    public ReadBoletoResponseDTO(Boleto boleto) {
        setId(boleto.getId());
        setEstadoPagamento(boleto.getEstadoPagamento().getEstadoAtual());
        setTipoPagamento(boleto.getTipoPagamento().getTipo());
        dataVencimento = boleto.getDataVencimento();
        dataPagamento = boleto.getDataVencimento();
    }

}
