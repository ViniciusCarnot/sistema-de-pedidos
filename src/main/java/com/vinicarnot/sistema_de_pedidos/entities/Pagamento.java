package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_pagamento")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EstadoPagamento estadoAtual;

}
