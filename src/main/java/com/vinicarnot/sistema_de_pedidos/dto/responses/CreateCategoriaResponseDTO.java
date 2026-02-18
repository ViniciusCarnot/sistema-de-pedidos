package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CreateCategoriaResponseDTO {

    @Setter
    private Long id;

    @Setter
    private String nome;

    private Set<CreateProdutoResponseDTO> produtos = new HashSet<>();

    public CreateCategoriaResponseDTO(Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
        for(Produto produto : categoria.getProdutos()) {
            produtos.add(new CreateProdutoResponseDTO(produto));
        }
    }

}
