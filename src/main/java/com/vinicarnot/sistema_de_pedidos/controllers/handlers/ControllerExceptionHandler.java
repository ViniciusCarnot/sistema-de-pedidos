package com.vinicarnot.sistema_de_pedidos.controllers.handlers;

import com.vinicarnot.sistema_de_pedidos.dto.ErroCustomizado;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ProdutoRepetidoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ProdutoRepetidoException.class)
    public ResponseEntity<ErroCustomizado> produtoRepetidoErro(ProdutoRepetidoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

}
