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
public class ReadProdutoResponseDTO {

    private String nome;
    private BigDecimal preco;

    public ReadProdutoResponseDTO(Produto produto) {
        nome = produto.getNome();
        preco = produto.getPreco();
    }

}
