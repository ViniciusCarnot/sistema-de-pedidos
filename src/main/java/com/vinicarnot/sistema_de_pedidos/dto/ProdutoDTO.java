package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "Nome é um campo obrigatório.")
    private String nome;

    @NotNull(message = "Preço é um campo obrigatório.")
    @Positive(message = "Preço deve ser um valor positivo.")
    private BigDecimal preco;

    public ProdutoDTO(Produto entity) {
        id = entity.getId();
        nome = entity.getNome();
        preco = entity.getPreco();
    }
}
