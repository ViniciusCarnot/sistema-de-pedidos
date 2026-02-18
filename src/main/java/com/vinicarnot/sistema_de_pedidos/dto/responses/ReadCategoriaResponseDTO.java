package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusProduto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ReadCategoriaResponseDTO {

    @Setter
    private Long id;

    @Setter
    private String nome;

    private Set<ReadProdutoResponseDTO> produtos = new HashSet<>();

    public ReadCategoriaResponseDTO(Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
        for(Produto produto : categoria.getProdutos()) {
            if(produto.getStatusProduto().equals(StatusProduto.ATIVO)) {
                produtos.add(new ReadProdutoResponseDTO(produto));
            }
        }
    }

}
