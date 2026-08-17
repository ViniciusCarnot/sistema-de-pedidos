package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;

import java.time.LocalDate;

public class PedidoFactory {

    public static Pedido instanciarPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setCliente(ClienteFactory.instanciarClienteNormal());
        pedido.setEnderecoDeEntrega(EnderecoFactory.instanciarEndereco());
        pedido.getItemsPedidos().add(new ItemPedido(ProdutoFactory.instanciarProduto(), pedido, 1));
        pedido.getItemsPedidos().add(new ItemPedido(ProdutoFactory.instanciarProduto2(), pedido, 1));
        return pedido;
    }

}
