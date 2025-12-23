package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "Nome é um campo obrigatório.")
    private String nome;

    private Set<ProdutoDTO> produtos = new HashSet<>();

    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaDTO(Categoria entity) {
        id = entity.getId();
        nome = entity.getNome();
        getProdutos().clear();
        for(Produto p : entity.getProdutos()) {
            produtos.add(new ProdutoDTO(p));
        }
    }
}
