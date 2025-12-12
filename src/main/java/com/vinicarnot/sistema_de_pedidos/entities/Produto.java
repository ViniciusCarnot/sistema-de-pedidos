package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(precision = 12, scale = 2)
    private BigDecimal preco;

    @ManyToMany(mappedBy = "produtos")
    private Set<Categoria> categorias;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Produto produto)) return false;

        return id.equals(produto.id) && nome.equals(produto.nome);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        return result;
    }
}
