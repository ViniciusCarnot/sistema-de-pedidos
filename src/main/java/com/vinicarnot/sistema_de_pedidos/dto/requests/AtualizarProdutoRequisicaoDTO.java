package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class AtualizarProdutoRequisicaoDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    @NotNull(message = "O campo 'preco' é obrigatório.")
    @PositiveOrZero(message = "O valor do campo 'preco' deve ser " +
            "positivo ou igual a zero.")
    private BigDecimal preco;

    @NotNull(message = "O campo 'statusProduto' é obrigatório.")
    private StatusProduto statusProduto;

}
