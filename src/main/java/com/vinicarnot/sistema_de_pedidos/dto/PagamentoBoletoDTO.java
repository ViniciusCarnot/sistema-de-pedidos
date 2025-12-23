package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PagamentoBoletoDTO extends PagamentoDTO {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

}
