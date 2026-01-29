package com.vinicarnot.sistema_de_pedidos.controllers.handlers;

import com.vinicarnot.sistema_de_pedidos.dto.ErroCustomizado;
import com.vinicarnot.sistema_de_pedidos.dto.ErroCustomizadoValidacao;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ClienteComDadosIncompletosException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoModificavelException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RecursoJaExistenteException.class)
    public ResponseEntity<ErroCustomizado> recurosJaExistenteErro(RecursoJaExistenteException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroCustomizado> recursoNaoEncontradoErro(RecursoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(ClienteComDadosIncompletosException.class)
    public ResponseEntity<ErroCustomizado> clienteComDadosIncompletosErro(ClienteComDadosIncompletosException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizadoValidacao erro = new ErroCustomizadoValidacao(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNaoModificavelException.class)
    public ResponseEntity<ErroCustomizado> recursoNaoModificavelErro(RecursoNaoModificavelException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroCustomizadoValidacao> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizadoValidacao erro = new ErroCustomizadoValidacao(Instant.now(), status.value(), "Dados inválidos.", request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()) {
            erro.adicionarErro(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroCustomizado> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroCustomizado erro = new ErroCustomizadoValidacao(Instant.now(), status.value(), "Recurso não encontrado no nosso banco de dados.", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

}
