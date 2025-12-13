package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_estado")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "nome"})
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(unique = true)
    @Setter
    private String nome;

    @OneToMany(mappedBy = "estado")
    private Set<Cidade> cidades = new HashSet<>();

    public Estado(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
