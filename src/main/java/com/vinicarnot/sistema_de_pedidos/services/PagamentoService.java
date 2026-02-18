package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pagamento;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PagamentoService {

    public Pagamento criarFormaDePagamento(Pedido pedido, CreatePagamentoRequestDTO dtoRequest) {
        if(dtoRequest instanceof CreateBoletoRequestDTO createBoletoRequestDTO) {
            Boleto boleto = new Boleto();
            boleto.setTipoPagamento(createBoletoRequestDTO.getTipoPagamento());
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setDataVencimento(LocalDate.now().plusMonths(3));
            boleto.setDataPagamento(null);
            boleto.setPedido(pedido);
            return boleto;
        } else if(dtoRequest instanceof CreateCartaoDeCreditoRequestDTO createCartaoDeCreditoRequestDTO) {
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

    public Pagamento atualizarFormaDePagamento(Pedido pedido, UpdatePagamentoRequestDTO dtoRequest) {
        if(dtoRequest instanceof UpdateBoletoRequestDTO boletoRequestDTO) {
            Boleto boleto = new Boleto();
            boleto.setTipoPagamento(boletoRequestDTO.getTipoPagamento());
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setDataVencimento(LocalDate.now().plusMonths(3));
            boleto.setDataPagamento(null);
            boleto.setPedido(pedido);
            return boleto;
        } else if(dtoRequest instanceof UpdateCartaoDeCreditoRequestDTO cartaoDeCreditoRequestDTO) {
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setTipoPagamento(cartaoDeCreditoRequestDTO.getTipoPagamento());
            cartaoDeCredito.setEstadoPagamento(EstadoPagamento.PENDENTE);
            cartaoDeCredito.setQuantidadeParcelas(cartaoDeCreditoRequestDTO.getQuantidadeDeParcelas());
            cartaoDeCredito.setPedido(pedido);
            return cartaoDeCredito;
        } else {
            throw new RecursoNaoEncontradoException("Forma de Pagamento inválida.");
        }
    }

}
