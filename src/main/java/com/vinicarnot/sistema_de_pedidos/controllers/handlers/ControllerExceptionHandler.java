package com.vinicarnot.sistema_de_pedidos.controllers.handlers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ErroCustomizado;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ErroCustomizadoValidacao;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RecursoJaExistenteException.class)
    public ResponseEntity<ErroCustomizado> recurosJaExistenteException(RecursoJaExistenteException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroCustomizado> recursoNaoEncontradoException(RecursoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(ClienteComDadosIncompletosException.class)
    public ResponseEntity<ErroCustomizado> clienteComDadosIncompletosException(ClienteComDadosIncompletosException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNegadoException.class)
    public ResponseEntity<ErroCustomizado> recursoNegadoException(RecursoNegadoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNaoModificavelException.class)
    public ResponseEntity<ErroCustomizado> recursoNaoModificavelException(RecursoNaoModificavelException e, HttpServletRequest request) {
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
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), "Recurso não encontrado no nosso banco de dados.", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("Erro na leitura do JSON: Valor de campo inválido ou fora do padrão.");
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErroCustomizado> authorizationDeniedException(AuthorizationDeniedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), "Cliente sem permissão para acessar este recurso.", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

}
