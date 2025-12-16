package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ProdutoDTO {

    @NotBlank(message = "Nome é um campo obrigatório.")
    private String nome;

    @NotNull(message = "Preço é um campo obrigatório")
    private BigDecimal preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, BigDecimal preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public ProdutoDTO(Produto entity) {
        nome = entity.getNome();
        preco = entity.getPreco();
    }
}
