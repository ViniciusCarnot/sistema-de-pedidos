package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ReadProdutoResponseAdminDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private String statusProduto;


    public ReadProdutoResponseAdminDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        preco = produto.getPreco();
        statusProduto = produto.getStatusProduto().getStatus();
    }

}
