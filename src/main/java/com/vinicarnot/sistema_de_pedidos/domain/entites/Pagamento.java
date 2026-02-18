package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_pagamento")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipoPagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
