package com.vinicarnot.sistema_de_pedidos.domain.entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_telefone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "numero"})
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
