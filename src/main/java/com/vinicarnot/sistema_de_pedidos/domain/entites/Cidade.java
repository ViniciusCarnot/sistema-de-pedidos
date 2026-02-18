package com.vinicarnot.sistema_de_pedidos.domain.entites;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_cidade")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "nome"})
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    @Setter
    private Estado estado;

    @OneToMany(mappedBy = "cidade")
    private Set<Endereco> enderecos = new HashSet<>();

    public Cidade(Long id, String nome, Estado estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }
}
