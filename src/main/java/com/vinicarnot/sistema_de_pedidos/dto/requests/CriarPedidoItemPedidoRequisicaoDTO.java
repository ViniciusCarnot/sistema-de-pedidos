package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class CriarPedidoItemPedidoRequisicaoDTO {

    @NotNull(message = "O campo 'produtoId' do Produto, da lista 'items' é obrigatório.")
    private Long produtoId;

    @NotNull(message = "O campo 'quantidade' do Produto, da lista 'items' é obrigatório.")
    @Positive(message = "O valor do campo 'quantidade' do Produto ,da lista 'items' deve ser positivo.")
    private Integer quantidade;

}
