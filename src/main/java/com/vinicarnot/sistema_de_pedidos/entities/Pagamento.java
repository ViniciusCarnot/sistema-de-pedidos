package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_pagamento")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
public abstract class Pagamento {

    @Id
    private Long id;

    @Setter
    private EstadoPagamento estadoAtual;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

}
