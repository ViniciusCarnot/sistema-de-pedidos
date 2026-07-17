package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class LerCategoriaProdutoRespostaDTO {

    @Setter
    private Long id;

    @Setter
    private String nome;

    private Set<LerProdutoRespostaDTO> produtos = new HashSet<>();

    public LerCategoriaProdutoRespostaDTO(Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
        for(Produto produto : categoria.getProdutos()) {
            if(produto.getDisponibilidade().equals(Disponibilidade.DISPONIVEL)) {
                produtos.add(new LerProdutoRespostaDTO(produto));
            }
        }
    }

}
