package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;

public class EnderecoFactory {

    public static Endereco instanciarEndereco() {
        return new Endereco
                (
                    1L,
                    "Rua Alemanha",
                    "1",
                    "Independência",
                    new Cidade(1L, "Campinas", new Estado(1L, "São Paulo")
                )
        );
    }

    public static Endereco instanciarEndereco2() {
        return new Endereco
                (
                        2L,
                        "Rua Brasil",
                        "2B",
                        "Independência",
                        new Cidade(2L, "Piracicaba", new Estado(1L, "São Paulo")
                        )
                );
    }

}
