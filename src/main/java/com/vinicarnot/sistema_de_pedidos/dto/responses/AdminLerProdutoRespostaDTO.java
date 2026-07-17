package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminLerProdutoRespostaDTO {

    private Long id;

    private String nome;

    private BigDecimal preco;

    private String disponibilidade;

    private Boolean visibilidade;

    public AdminLerProdutoRespostaDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        preco = produto.getPreco();
        disponibilidade = produto.getDisponibilidade().getStatus();
        visibilidade = produto.getVisibilidade();
    }

}
