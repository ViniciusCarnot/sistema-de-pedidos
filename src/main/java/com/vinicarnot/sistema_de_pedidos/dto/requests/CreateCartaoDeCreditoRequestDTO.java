package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCartaoDeCreditoRequestDTO extends CreatePagamentoRequestDTO {

    @NotNull(message = "O campo 'Quantidade de Parcelas' do Cartão de Crédito é obrigatório.")
    private Integer quantidadeDeParcelas;

}
