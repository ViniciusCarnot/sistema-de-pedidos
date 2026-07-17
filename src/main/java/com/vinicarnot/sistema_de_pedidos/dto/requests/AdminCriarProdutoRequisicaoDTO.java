package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AdminCriarProdutoRequisicaoDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    @NotNull(message = "O campo 'preco' é obrigatório.")
    @PositiveOrZero(message = "O valor do campo 'preco' deve ser " +
            "positivo ou igual a zero.")
    private BigDecimal preco;

    @NotNull(message = "O campo 'disponibilidade' é obrigatório.")
    private Disponibilidade disponibilidade;

    @NotNull(message = "O campo 'visibilidade' é obrigatório.")
    private Boolean visibilidade;

}
