package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_cidade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "nome"})
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @OneToMany(mappedBy = "cidade")
    private Set<Endereco> enderecos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

}
