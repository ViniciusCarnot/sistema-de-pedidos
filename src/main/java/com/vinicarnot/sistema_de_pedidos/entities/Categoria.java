package com.vinicarnot.sistema_de_pedidos.entities;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_categoria")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "nome"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(unique = true)
    @Setter
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "tb_categoria_produto",
            joinColumns = @JoinColumn(name = "categoria_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private Set<Produto> produtos = new HashSet<>();

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
