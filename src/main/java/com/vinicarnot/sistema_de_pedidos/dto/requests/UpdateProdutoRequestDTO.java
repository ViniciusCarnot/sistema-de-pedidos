package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProdutoRequestDTO {

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    @NotNull(message = "O campo 'Preço' é obrigatório.")
    @PositiveOrZero(message = "O valor do campo 'Preço' deve ser " +
            "positivo ou igual a zero.")
    private BigDecimal preco;

}
