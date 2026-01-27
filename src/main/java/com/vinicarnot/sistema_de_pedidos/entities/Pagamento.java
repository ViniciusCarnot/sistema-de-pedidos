package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_pagamento")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipoPagamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public abstract class Pagamento {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoPagamento estadoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private TipoPagamento tipoPagamento;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

}
