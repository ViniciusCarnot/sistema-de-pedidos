package com.vinicarnot.sistema_de_pedidos.services.exceptions;

public class ProcessamentoPagamentoExcessao extends RuntimeException {
    public ProcessamentoPagamentoExcessao(String message) {
        super(message);
    }
}
