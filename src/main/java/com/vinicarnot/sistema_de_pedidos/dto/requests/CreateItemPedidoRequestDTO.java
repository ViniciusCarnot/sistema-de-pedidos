package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CreateItemPedidoRequestDTO {

    @NotNull(message = "O campo 'id' do Produto, da lista 'items' é obrigatório.")
    private Long idProduto;

    @NotNull(message = "O campo 'precoPago' do Produto, da lista 'items' é obrigatório.")
    @PositiveOrZero(message = "O valor do campo 'precoPago' do Produto, da lista 'items' deve " +
            "ser positivo ou igual a zero.")
    private BigDecimal precoPago;

    @NotNull(message = "O campo 'quantidade' do Produto, da lista 'items' é obrigatório.")
    @Positive(message = "O valor do campo 'quantidade' do Produto ,da lista 'items' deve ser positivo.")
    private Integer quantidade;

}
