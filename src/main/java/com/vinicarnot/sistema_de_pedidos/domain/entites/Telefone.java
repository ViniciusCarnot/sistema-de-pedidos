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

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Telefone(String numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
    }

}
