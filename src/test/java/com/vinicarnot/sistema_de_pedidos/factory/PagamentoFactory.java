package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;

import java.time.LocalDate;

public class PagamentoFactory {

    public static CartaoDeCredito instanciarCartaoDeCredito() {
        return new CartaoDeCredito(
                1L,
                EstadoPagamento.PENDENTE,
                TipoPagamento.CARTAO_DE_CREDITO,
                PedidoFactory.instanciarPedido(),
                3,
                false,
                LocalDate.now().plusMonths(3)
        );
    }

    public static Boleto instanciarBoleto() {
        Pedido pedido = PedidoFactory.instanciarPedido();
        return new Boleto(
                2L,
                EstadoPagamento.PENDENTE,
                TipoPagamento.BOLETO,
                pedido,
                pedido.getCliente().getCpfOuCnpj(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getEmail(),
                "...",
                LocalDate.now().plusDays(3)
        );
    }

}
