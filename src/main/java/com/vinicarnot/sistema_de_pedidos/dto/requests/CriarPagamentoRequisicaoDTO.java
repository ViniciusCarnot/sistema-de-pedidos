package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CriarPagamentoRequisicaoDTO {

    @NotNull(message = "O campo 'tipoPagamento' é obrigatório.")
    private TipoPagamento tipoPagamento;

    @Valid
    private CriarBoletoRequisicaoDTO boleto;

    @Valid
    private CriarCartaoDeCreditoRequisicaoDTO cartaoDeCredito;

}
