package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_produto")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "nome"})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(unique = true)
    @Setter
    private String nome;

    @Column(precision = 12, scale = 2)
    @Setter
    private BigDecimal preco;

    @ManyToMany(mappedBy = "produtos")
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "id.produto")
    private Set<ItemPedido> itemsPedidos = new HashSet<>();

    public List<Pedido> getPedidos() {
        return itemsPedidos.stream().map(itemPedido -> itemPedido.getPedido()).toList();
    }

}
