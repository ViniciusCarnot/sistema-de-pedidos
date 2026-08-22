package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;

public class CategoriaFactory {

    public static Categoria instanciarCategoria() {
        Categoria categoria = new Categoria(1L, "Jogos");
        categoria.getProdutos().add(ProdutoFactory.instanciarProduto());
        categoria.getProdutos().add(ProdutoFactory.instanciarProduto2());
        return categoria;
    }

}
