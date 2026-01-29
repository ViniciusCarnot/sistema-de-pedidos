package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.entities.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PagamentoService {

    public Pagamento criarFormaDePagamento(Pedido pedido, CreatePagamentoRequestDTO createPagamentoRequestDTO) {
        if(createPagamentoRequestDTO instanceof CreateBoletoRequestDTO createBoletoRequestDTO) {
            Boleto boleto = new Boleto();
            boleto.setTipoPagamento(createBoletoRequestDTO.getTipoPagamento());
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setDataVencimento(LocalDate.now().plusMonths(3));
            boleto.setPedido(pedido);
            return boleto;
        } else if(createPagamentoRequestDTO instanceof CreateCartaoDeCreditoRequestDTO createCartaoDeCreditoRequestDTO) {
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setTipoPagamento(createCartaoDeCreditoRequestDTO.getTipoPagamento());
            cartaoDeCredito.setEstadoPagamento(EstadoPagamento.PENDENTE);
            cartaoDeCredito.setQuantidadeParcelas(createCartaoDeCreditoRequestDTO.getQuantidadeDeParcelas());
            cartaoDeCredito.setPedido(pedido);
            return cartaoDeCredito;
        } else {
            throw new RecursoNaoEncontradoException("Forma de Pagamento inválida.");
        }
    }

    public Pagamento atualizarFormaDePagamento(Pedido pedido, UpdatePagamentoRequestDTO updatePagamentoRequestDTO) {
        if(updatePagamentoRequestDTO instanceof UpdateBoletoRequestDTO updateBoletoRequestDTO) {
            Boleto boleto = new Boleto();
            boleto.setTipoPagamento(updateBoletoRequestDTO.getTipoPagamento());
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setDataVencimento(LocalDate.now().plusMonths(3));
            boleto.setPedido(pedido);
            return boleto;
        } else if(updatePagamentoRequestDTO instanceof UpdateCartaoDeCreditoRequestDTO updateCartaoDeCreditoRequestDTO) {
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setTipoPagamento(updateCartaoDeCreditoRequestDTO.getTipoPagamento());
            cartaoDeCredito.setEstadoPagamento(EstadoPagamento.PENDENTE);
            cartaoDeCredito.setQuantidadeParcelas(updateCartaoDeCreditoRequestDTO.getQuantidadeDeParcelas());
            cartaoDeCredito.setPedido(pedido);
            return cartaoDeCredito;
        } else {
            throw new RecursoNaoEncontradoException("Forma de Pagamento inválida.");
        }
    }

}
