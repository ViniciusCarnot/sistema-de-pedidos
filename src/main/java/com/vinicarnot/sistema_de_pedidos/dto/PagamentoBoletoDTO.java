package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PagamentoBoletoDTO extends PagamentoDTO {

    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

}
