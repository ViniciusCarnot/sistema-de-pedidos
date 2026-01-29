package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProdutoResponseDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;

    public CreateProdutoResponseDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        preco = produto.getPreco();
    }

}
