package com.vinicarnot.sistema_de_pedidos.services.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

}
