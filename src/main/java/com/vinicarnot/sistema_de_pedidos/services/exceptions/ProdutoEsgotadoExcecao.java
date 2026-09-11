package com.vinicarnot.sistema_de_pedidos.services.exceptions;

public class ProdutoEsgotadoExcecao extends RuntimeException {
    public ProdutoEsgotadoExcecao(String message) {
        super(message);
    }
}
