package com.vinicarnot.sistema_de_pedidos.services.exceptions;

public class ProdutoEsgotadoException extends RuntimeException {
    public ProdutoEsgotadoException(String message) {
        super(message);
    }
}
