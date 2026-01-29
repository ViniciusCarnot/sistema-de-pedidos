package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemPedidoRequestDTO {

    @NotNull(message = "O campo 'Id' do Produto da lista de items é obrigatório.")
    private Long id;

    @NotNull(message = "O campo 'Preço' do Produto da lista de items é obrigatório.")
    @PositiveOrZero(message = "O valor do campo 'Preço' do Produto da lista de items deve " +
            "ser positivo ou igual a zero.")
    private BigDecimal preco;

    @NotNull(message = "O campo 'Quantidade' do Produto da lista de items é obrigatório.")
    @Positive(message = "O valor do campo 'Quantidade' do Produto da lista de items deve ser positivo.")
    private Integer quantidade;

}
