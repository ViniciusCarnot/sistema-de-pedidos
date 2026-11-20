package com.vinicarnot.sistema_de_pedidos.controllers.handlers;

import com.vinicarnot.sistema_de_pedidos.dto.responses.ErroCustomizado;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ErroCustomizadoValidacao;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    // --- Minhas Excecoes Customizadas ---

    @ExceptionHandler(RecursoJaExistenteExcecao.class)
    public ResponseEntity<ErroCustomizado> recurosJaExistenteExcecao(RecursoJaExistenteExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNaoEncontradoExcecao.class)
    public ResponseEntity<ErroCustomizado> recursoNaoEncontradoExcecao(RecursoNaoEncontradoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(RecursoNegadoExcecao.class)
    public ResponseEntity<ErroCustomizado> recursoNegadoExcecao(RecursoNegadoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(ProdutoEsgotadoExcecao.class)
    public ResponseEntity<ErroCustomizado> produtoEsgotadoExcecao(ProdutoEsgotadoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(PedidoCancelamentoExcecao.class)
    public ResponseEntity<ErroCustomizado> produtoCancelamentoExcecao(PedidoCancelamentoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(ProcessamentoPagamentoExcecao.class)
    public ResponseEntity<ErroCustomizado> processamentoPagamentoExcecao(ProcessamentoPagamentoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(LimiteAtingidoExcecao.class)
    public ResponseEntity<ErroCustomizado> limiteAtingidoExcecao(LimiteAtingidoExcecao e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // --- Excecoes do Spring ---

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErroCustomizadoValidacao> handlerMethodValidationException(
            HandlerMethodValidationException e,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErroCustomizadoValidacao erro = new ErroCustomizadoValidacao(
                Instant.now(),
                status.value(),
                "Dados inválidos.",
                request.getRequestURI()
        );

        for (ParameterValidationResult result : e.getParameterValidationResults()) {
            Integer index = result.getContainerIndex(); // útil quando o argumento é uma List

            for (MessageSourceResolvable resolvable : result.getResolvableErrors()) {
                String campo = resolverNomeCampo(resolvable, index);
                String mensagem = resolvable.getDefaultMessage();
                erro.adicionarErro(campo, mensagem);
            }
        }

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
    public ResponseEntity<ErroCustomizado> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), "Erro na leitura do JSON: Valor de campo inválido ou fora do padrão.", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroCustomizado> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), "Acesso negado. Você não possui a permissão necessária para acessar este recurso.",
                request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErroCustomizado> usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErroCustomizado erro = new ErroCustomizado(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    private String resolverNomeCampo(MessageSourceResolvable resolvable, Integer index) {
        String[] codes = resolvable.getCodes();
        String nomeCampo = "campo";

        if (codes != null && codes.length > 0) {
            // Ex.: "NotBlank.atualizarMeuEnderecoRequisicaoDTOList.logradouro" -> pega "logradouro"
            String ultimoCodigo = codes[0];
            int ultimoPonto = ultimoCodigo.lastIndexOf('.');
            if (ultimoPonto != -1 && ultimoPonto < ultimoCodigo.length() - 1) {
                nomeCampo = ultimoCodigo.substring(ultimoPonto + 1);
            }
        }

        // Se vier de uma lista, inclui o índice para facilitar o diagnóstico no front-end/Postman
        return (index != null) ? nomeCampo + "[" + index + "]" : nomeCampo;
    }

}
