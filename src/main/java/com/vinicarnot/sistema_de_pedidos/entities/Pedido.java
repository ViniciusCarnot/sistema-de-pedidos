package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    private Long id;

    private Instant instanteDaCompra;

    private Pagamento pagamento;

    private Cliente cliente;

    private Endereco enderecoDeEntrega;

    private Set<ItemPedido> items;

}
