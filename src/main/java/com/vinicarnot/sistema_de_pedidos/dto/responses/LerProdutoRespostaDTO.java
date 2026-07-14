package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LerProdutoRespostaDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private String statusProduto;

    public LerProdutoRespostaDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        preco = produto.getPreco();
        statusProduto = produto.getStatusProduto().getStatus();
    }

}
