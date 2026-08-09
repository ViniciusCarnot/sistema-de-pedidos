package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;

public class CategoriaFactory {

    public static Categoria instanciarCategoria() {
        return new Categoria(1L, "Jogos");
    }

}
